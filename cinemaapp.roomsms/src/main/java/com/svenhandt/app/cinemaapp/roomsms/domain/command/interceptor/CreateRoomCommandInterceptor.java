package com.svenhandt.app.cinemaapp.roomsms.domain.command.interceptor;

import com.svenhandt.app.cinemaapp.roomsms.domain.command.interceptor.exception.RoomAlreadyCreatedException;
import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.CreateRoomCommand;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.RoomView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.repository.RoomViewsRepository;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateRoomCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final RoomViewsRepository roomViewsRepository;

    public CreateRoomCommandInterceptor(RoomViewsRepository roomViewsRepository) {
        this.roomViewsRepository = roomViewsRepository;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            if(CreateRoomCommand.class.equals(command.getPayloadType())) {
                CreateRoomCommand createRoomCommand = (CreateRoomCommand) command.getPayload();
                checkRoomForCommandAlreadyCreated(createRoomCommand);
            }
            return command;
        };
    }

    private void checkRoomForCommandAlreadyCreated(CreateRoomCommand command) {
        if(StringUtils.isNotBlank(command.getTargetId())) {
            List<RoomView> roomViews = roomViewsRepository.findAll();
            if(roomViews != null) {
                List<String> roomIds = roomViews.stream().map(RoomView::getId).toList();
                if(roomIds.contains(command.getTargetId())) {
                    throw new RoomAlreadyCreatedException("Room with id " + command.getTargetId() + " already created.");
                }
            }
        }
    }
}
