package synapse.dementia.domain.users.game.multiplayergame.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameQuestion;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameResult;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameRoom;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameUser;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiplayerGame;
import synapse.dementia.domain.users.game.multiplayergame.repository.MultiGameQuestionRepository;
import synapse.dementia.domain.users.game.multiplayergame.repository.MultiGameResultRepository;
import synapse.dementia.domain.users.game.multiplayergame.repository.MultiGameRoomRepository;
import synapse.dementia.domain.users.game.multiplayergame.repository.MultiGameUserRepository;
import synapse.dementia.domain.users.game.multiplayergame.repository.MultiplayerGameRepository;
import synapse.dementia.domain.users.member.domain.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class MultiplayerGameService {

    private final MultiGameQuestionRepository questionRepository;
    private final MultiGameResultRepository resultRepository;
    private final MultiGameRoomRepository roomRepository;
    private final MultiGameUserRepository userRepository;
    private final MultiplayerGameRepository gameRepository;

    public MultiplayerGameService(MultiGameQuestionRepository questionRepository,
                                  MultiGameResultRepository resultRepository,
                                  MultiGameRoomRepository roomRepository,
                                  MultiGameUserRepository userRepository,
                                  MultiplayerGameRepository gameRepository) {
        this.questionRepository = questionRepository;
        this.resultRepository = resultRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    public void init() {
        if (roomRepository.count() == 0) {
            createDefaultRooms();
        }
    }

    // 기본 방 4개 생성
    public void createDefaultRooms() {
        for (int i = 1; i <= 4; i++) {
            String roomName = "대기실 " + i;
            if (roomRepository.findByRoomName(roomName).isEmpty()) {
                createRoom(roomName);
            }
        }
    }

    // 방 생성
    public MultiGameRoom createRoom(String roomName) {
        MultiGameRoom room = MultiGameRoom.builder()
                .roomName(roomName)
                .build();
        roomRepository.save(room);

        // 10개의 문제 세트 생성 및 저장
        for (int i = 0; i < 10; i++) {
            MultiGameQuestion question = generateArithmeticQuestion(room);
            questionRepository.save(question);
        }

        return room;
    }

    // 대기실에 접속
    public MultiGameUser joinRoom(Long roomId, Users user) {
        MultiGameRoom room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        MultiGameUser gameUser = MultiGameUser.builder()
                .multiGameRoom(room)
                .user(user)
                .build();
        return userRepository.save(gameUser);
    }

    // 대기실에 사람이 2명 이상이면 게임 시작
    public MultiplayerGame startGame(Long roomId) {
        MultiGameRoom room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        List<MultiGameUser> usersInRoom = userRepository.findByMultiGameRoom(room);

        if (usersInRoom.size() < 2) {
            throw new IllegalStateException("At least 2 users required to start the game");
        }

        MultiplayerGame game = MultiplayerGame.builder()
                .multiGameRoom(room)
                .isStarted(true)
                .build();
        return gameRepository.save(game);
    }

    // 방에 있는 사용자들 조회
    public List<MultiGameUser> getUsersInRoom(Long roomId) {
        MultiGameRoom room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        return userRepository.findByMultiGameRoom(room);
    }

    // 모든 방 조회
    public List<MultiGameRoom> getAllRooms() {
        return roomRepository.findAll();
    }

    // 방 정보 조회
    public MultiGameRoom getRoomInfo(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
    }

    // 기본적인 사칙연산 문제 생성
    public MultiGameQuestion generateArithmeticQuestion(MultiGameRoom room) {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        int c = rand.nextInt(10) + 1;
        char[] operations = {'+', '-', '*', '/'};
        char operation1 = operations[rand.nextInt(operations.length)];
        char operation2 = operations[rand.nextInt(operations.length)];

        String questionText;
        int answer;

        // 첫 번째 연산 적용
        int intermediateResult;
        switch (operation1) {
            case '+':
                intermediateResult = a + b;
                break;
            case '-':
                intermediateResult = a - b;
                break;
            case '*':
                intermediateResult = a * b;
                break;
            case '/':
                while (b == 0 || a % b != 0) {
                    b = rand.nextInt(10) + 1;
                    a = b * (rand.nextInt(10) + 1);
                }
                intermediateResult = a / b;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation1);
        }

        // 두 번째 연산 적용
        switch (operation2) {
            case '+':
                answer = intermediateResult + c;
                questionText = intermediateResult + " " + operation2 + " " + c;
                break;
            case '-':
                answer = intermediateResult - c;
                questionText = intermediateResult + " " + operation2 + " " + c;
                break;
            case '*':
                while (c > 10) {
                    c = rand.nextInt(10) + 1;
                }
                answer = intermediateResult * c;
                questionText = intermediateResult + " " + operation2 + " " + c;
                break;
            case '/':
                while (c == 0 || intermediateResult % c != 0) {
                    c = rand.nextInt(10) + 1;
                }
                answer = intermediateResult / c;
                questionText = intermediateResult + " " + operation2 + " " + c;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation2);
        }

        questionText = a + " " + operation1 + " " + b + " " + operation2 + " " + c;
        String answerText = String.valueOf(answer);
        MultiGameQuestion question = MultiGameQuestion.builder()
                .question(questionText)
                .answer(answerText)
                .multiGameRoom(room)
                .build();
        return question;
    }

    // 정답 제출
    public boolean submitAnswer(Long userId, Long questionId, String answer) {
        MultiGameQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));
        MultiGameUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        user.setAnswer(answer);
        userRepository.save(user);
        return question.getAnswer().equals(answer);
    }

    // 사용자 순위 계산
    public List<MultiGameResult> calculateRanking(Long roomId) {
        MultiGameRoom room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        List<MultiGameUser> usersInRoom = userRepository.findByMultiGameRoom(room);

        List<MultiGameResult> results = new ArrayList<>();
        for (MultiGameUser user : usersInRoom) {
            List<MultiGameQuestion> questions = questionRepository.findByMultiGameRoom(room);
            int correctAnswers = 0;
            for (MultiGameQuestion question : questions) {
                if (question.getAnswer().equals(user.getAnswer())) {
                    correctAnswers++;
                }
            }
            results.add(MultiGameResult.builder()
                    .multiplayerGame(user.getMultiGameRoom().getMultiplayerGame())
                    .user(user.getUser())
                    .correctAnswer(correctAnswers)
                    .build());
        }
        results.sort((r1, r2) -> r2.getCorrectAnswer() - r1.getCorrectAnswer());
        return results;
    }
}