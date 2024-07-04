package synapse.dementia.domain.users.game.multiplayergame.controller;

import org.springframework.web.bind.annotation.*;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameQuestion;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameRoom;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameUser;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiplayerGame;
import synapse.dementia.domain.users.game.multiplayergame.dto.request.CreateRoomRequest;
import synapse.dementia.domain.users.game.multiplayergame.dto.request.JoinRoomRequest;
import synapse.dementia.domain.users.game.multiplayergame.dto.request.SubmitAnswerRequest;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.MultiGameQuestionResponse;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.MultiGameRoomResponse;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.MultiGameUserResponse;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.MultiplayerGameResponse;
import synapse.dementia.domain.users.game.multiplayergame.service.MultiplayerGameService;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.service.UsersService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/multiplayer")
public class MultiplayerGameController {

    private final MultiplayerGameService multiplayerGameService;
    private final UsersService usersService;

    public MultiplayerGameController(MultiplayerGameService multiplayerGameService, UsersService usersService) {
        this.multiplayerGameService = multiplayerGameService;
        this.usersService = usersService;
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
        return new MultiGameUserResponse(gameUser.getIdx(), gameUser.getMultiGameRoom().getRoomIdx(), gameUser.getUser().getUsersIdx());
    }

    @GetMapping("/room-users")
    public List<MultiGameUserResponse> getUsersInRoom(@RequestParam Long roomId) {
        List<MultiGameUser> usersInRoom = multiplayerGameService.getUsersInRoom(roomId);
        return usersInRoom.stream()
                .map(user -> new MultiGameUserResponse(user.getIdx(), user.getMultiGameRoom().getRoomIdx(), user.getUser().getUsersIdx()))
                .collect(Collectors.toList());
    }

    @PostMapping("/start-game")
    public MultiplayerGameResponse startGame(@RequestParam Long roomId) {
        MultiplayerGame game = multiplayerGameService.startGame(roomId);
        return new MultiplayerGameResponse(game.getGameIdx(), game.getMultiGameRoom().getRoomIdx(), game.isStarted());
    }

    @GetMapping("/generate-question")
    public MultiGameQuestionResponse generateArithmeticQuestion() {
        MultiGameQuestion question = multiplayerGameService.generateArithmeticQuestion();
        return new MultiGameQuestionResponse(question.getQuestionIdx(), question.getQuestion(), question.getAnswer());
    }

    @PostMapping("/submit-answer")
    public boolean submitAnswer(@RequestBody SubmitAnswerRequest submitAnswerRequest) {
        return multiplayerGameService.submitAnswer(submitAnswerRequest.questionId(), submitAnswerRequest.answer());
    }
}
