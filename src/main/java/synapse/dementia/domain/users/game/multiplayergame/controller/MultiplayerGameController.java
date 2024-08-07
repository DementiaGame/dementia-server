package synapse.dementia.domain.users.game.multiplayergame.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.users.game.multiplayergame.domain.*;
import synapse.dementia.domain.users.game.multiplayergame.dto.request.CreateRoomRequest;
import synapse.dementia.domain.users.game.multiplayergame.dto.request.JoinRoomRequest;
import synapse.dementia.domain.users.game.multiplayergame.dto.request.SubmitAnswerRequest;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.*;
import synapse.dementia.domain.users.game.multiplayergame.service.MultiplayerGameService;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.service.UsersService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/multiplayer")
public class MultiplayerGameController {

    private final MultiplayerGameService multiplayerGameService;
    private final UsersService usersService;
    private final SimpMessagingTemplate messagingTemplate;

    public MultiplayerGameController(MultiplayerGameService multiplayerGameService, UsersService usersService, SimpMessagingTemplate messagingTemplate) {
        this.multiplayerGameService = multiplayerGameService;
        this.usersService = usersService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/create-room")
    public MultiGameRoomResponse createRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        MultiGameRoom room = multiplayerGameService.createRoom(createRoomRequest.roomName());
        return new MultiGameRoomResponse(room.getRoomIdx(), room.getRoomName());
    }

    @PostMapping("/join-room")
    public MultiGameUserResponse joinRoom(@RequestBody JoinRoomRequest joinRoomRequest) {
        Users user = usersService.findUserById(joinRoomRequest.userId());
        MultiGameUser gameUser = multiplayerGameService.joinRoom(joinRoomRequest.roomId(), user);

        MultiGameUserResponse response = new MultiGameUserResponse(gameUser.getIdx(), gameUser.getMultiGameRoom().getRoomIdx(), gameUser.getUser().getUsersIdx(), gameUser.getUser().getNickName());
        messagingTemplate.convertAndSend("/topic/room/" + joinRoomRequest.roomId(), multiplayerGameService.getUsersInRoom(joinRoomRequest.roomId()));

        return response;
    }

    @PostMapping("/leave-room")
    public void leaveRoom(@RequestBody JoinRoomRequest joinRoomRequest) {
        multiplayerGameService.leaveRoom(joinRoomRequest.roomId(), joinRoomRequest.userId());
        messagingTemplate.convertAndSend("/topic/room/" + joinRoomRequest.roomId(), multiplayerGameService.getUsersInRoom(joinRoomRequest.roomId()));
    }

    @GetMapping("/room-users")
    public List<MultiGameUserResponse> getUsersInRoom(@RequestParam Long roomId) {
        return multiplayerGameService.getUsersInRoom(roomId);
    }

    @PostMapping("/start-game")
    public ResponseEntity<?> startGame(@RequestBody Map<String, Long> requestBody) {
        try {
            Long roomId = requestBody.get("roomId");
            MultiplayerGame game = multiplayerGameService.startGame(roomId);

            // 게임이 시작되었음을 방의 모든 사용자에게 알림
            messagingTemplate.convertAndSend("/topic/room/" + roomId, new GameStartResponse(game.getGameIdx(), game.getMultiGameRoom().getRoomIdx(), game.isStarted()));

            return ResponseEntity.ok(new MultiplayerGameResponse(game.getGameIdx(), game.getMultiGameRoom().getRoomIdx(), game.isStarted()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid room ID");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown Exception: " + e.getMessage());
        }
    }

    @GetMapping("/generate-question")
    public MultiGameQuestionResponse generateArithmeticQuestion() {
        MultiGameQuestion question = multiplayerGameService.generateArithmeticQuestion(null);
        return new MultiGameQuestionResponse(question.getQuestionIdx(), question.getQuestion(), question.getAnswer());
    }

    @PostMapping("/submit-answer")
    public boolean submitAnswer(@RequestBody SubmitAnswerRequest submitAnswerRequest) {
        return multiplayerGameService.submitAnswer(submitAnswerRequest.userId(), submitAnswerRequest.questionId(), submitAnswerRequest.answer());
    }

    @GetMapping("/rankings")
    public List<MultiGameResultResponse> getRankings(@RequestParam Long roomId) {
        List<MultiGameResult> results = multiplayerGameService.calculateRanking(roomId);
        return results.stream()
                .map(result -> new MultiGameResultResponse(result.getResultIdx(), result.getMultiplayerGame().getGameIdx(), result.getUser().getUsersIdx(), result.getCorrectAnswer()))
                .collect(Collectors.toList());
    }

    @GetMapping("/room/{roomId}")
    public MultiGameRoomResponse getRoomInfo(@PathVariable Long roomId) {
        MultiGameRoom room = multiplayerGameService.getRoomInfo(roomId);
        return new MultiGameRoomResponse(room.getRoomIdx(), room.getRoomName());
    }

    @GetMapping("/rooms")
    public List<MultiGameRoomResponse> getAllRooms() {
        List<MultiGameRoom> rooms = multiplayerGameService.getAllRooms();
        return rooms.stream()
                .map(room -> new MultiGameRoomResponse(room.getRoomIdx(), room.getRoomName()))
                .collect(Collectors.toList());
    }
}
