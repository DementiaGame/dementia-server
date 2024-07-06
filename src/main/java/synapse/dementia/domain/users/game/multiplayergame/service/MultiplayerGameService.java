package synapse.dementia.domain.users.game.multiplayergame.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.users.game.multiplayergame.domain.*;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.MultiGameUserResponse;
import synapse.dementia.domain.users.game.multiplayergame.repository.*;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.service.UsersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class MultiplayerGameService {

    private final MultiGameQuestionRepository questionRepository;
    private final MultiGameResultRepository resultRepository;
    private final MultiGameRoomRepository roomRepository;
    private final MultiGameUserRepository userRepository;
    private final MultiplayerGameRepository gameRepository;

    private final UsersService usersService;

    public MultiplayerGameService(MultiGameQuestionRepository questionRepository,
                                  MultiGameResultRepository resultRepository,
                                  MultiGameRoomRepository roomRepository,
                                  MultiGameUserRepository userRepository,
                                  MultiplayerGameRepository gameRepository, UsersService usersService) {
        this.questionRepository = questionRepository;
        this.resultRepository = resultRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.usersService = usersService;
    }

    @PostConstruct
    public void init() {
        if (roomRepository.count() == 0) {
            createDefaultRooms();
        }
    }

    //중복 ID 못들어가도록
    public boolean isUserInRoom(Long roomId, Long userId) {
        List<MultiGameUserResponse> usersInRoom = getUsersInRoom(roomId);
        return usersInRoom.stream().anyMatch(user -> user.usersIdx().equals(userId));
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

        List<MultiGameUser> usersInRoom = userRepository.findByMultiGameRoom(room);
        boolean userExists = usersInRoom.stream()
                .anyMatch(gameUser -> gameUser.getUser().getUsersIdx().equals(user.getUsersIdx()));

        if (!userExists) {
            MultiGameUser gameUser = MultiGameUser.builder()
                    .multiGameRoom(room)
                    .user(user)
                    .build();
            return userRepository.save(gameUser);
        }

        return usersInRoom.stream()
                .filter(gameUser -> gameUser.getUser().getUsersIdx().equals(user.getUsersIdx()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found in room"));
    }

    public void leaveRoom(Long roomId, Long userId) {
        MultiGameRoom room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        Users user = usersService.findUserById(userId);
        MultiGameUser gameUser = userRepository.findByMultiGameRoomAndUser(room, user)
                .orElseThrow(() -> new IllegalArgumentException("User not found in the room"));

        userRepository.delete(gameUser);
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
    public List<MultiGameUserResponse> getUsersInRoom(Long roomId) {
        MultiGameRoom room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        List<MultiGameUser> usersInRoom = userRepository.findByMultiGameRoom(room);

        return usersInRoom.stream()
                .map(user -> new MultiGameUserResponse(user.getIdx(), user.getMultiGameRoom().getRoomIdx(), user.getUser().getUsersIdx(), user.getUser().getNickName()))
                .collect(Collectors.toList());
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
        MultiplayerGame game = room.getMultiplayerGame(); // 게임 객체 설정

        for (MultiGameUser user : usersInRoom) {
            List<MultiGameQuestion> questions = questionRepository.findByMultiGameRoom(room);
            int correctAnswers = 0;
            for (MultiGameQuestion question : questions) {
                if (question.getAnswer().equals(user.getAnswer())) {
                    correctAnswers++;
                }
            }
            results.add(MultiGameResult.builder()
                    .multiplayerGame(game)
                    .user(user.getUser())
                    .correctAnswer(correctAnswers)
                    .build());
        }
        results.sort((r1, r2) -> r2.getCorrectAnswer() - r1.getCorrectAnswer());
        return results;
    }

}
