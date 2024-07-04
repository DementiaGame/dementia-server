package synapse.dementia.domain.users.game.multiplayergame.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.users.game.multiplayergame.domain.*;
import synapse.dementia.domain.users.game.multiplayergame.repository.*;
import synapse.dementia.domain.users.member.domain.Users;

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

    // 방 생성
    public MultiGameRoom createRoom(String roomName) {
        MultiGameRoom room = MultiGameRoom.builder()
                .roomName(roomName)
                .build();
        return roomRepository.save(room);
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

    // 기본적인 사칙연산 문제 생성
    public MultiGameQuestion generateArithmeticQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;  // 1부터 10까지의 숫자
        int b = rand.nextInt(10) + 1;  // 1부터 10까지의 숫자
        int c = rand.nextInt(10) + 1;  // 1부터 10까지의 숫자
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
                // 나눗셈의 경우 나머지가 없는 문제만 출제
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
                // 두 자리 수끼리의 곱셈은 제외
                while (c > 10) {
                    c = rand.nextInt(10) + 1;
                }
                answer = intermediateResult * c;
                questionText = intermediateResult + " " + operation2 + " " + c;
                break;
            case '/':
                // 나눗셈의 경우 나머지가 없는 문제만 출제
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
                .build();
        return questionRepository.save(question);
    }


    // 정답 맞히기
    public boolean submitAnswer(Long questionId, String answer) {
        MultiGameQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));
        return question.getAnswer().equals(answer);
    }
}
