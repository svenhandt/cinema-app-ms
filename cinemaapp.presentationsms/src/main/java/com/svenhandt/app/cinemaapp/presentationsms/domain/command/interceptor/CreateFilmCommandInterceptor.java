package com.svenhandt.app.cinemaapp.presentationsms.domain.command.interceptor;

import com.svenhandt.app.cinemaapp.presentationsms.domain.command.interceptor.exception.FilmAlreadyCreatedException;
import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.CreateFilmCommand;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.FilmView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.repository.FilmViewsRepository;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateFilmCommandInterceptor  implements MessageDispatchInterceptor<CommandMessage<?>> {

    private FilmViewsRepository filmViewsRepository;

    public CreateFilmCommandInterceptor(FilmViewsRepository filmViewsRepository) {
        this.filmViewsRepository = filmViewsRepository;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            if(CreateFilmCommand.class.equals(command.getPayloadType())) {
                CreateFilmCommand createRoomCommand = (CreateFilmCommand) command.getPayload();
                checkFilmForCommandAlreadyCreated(createRoomCommand);
            }
            return command;
        };
    }

    private void checkFilmForCommandAlreadyCreated(CreateFilmCommand command) {
        if(StringUtils.isNotBlank(command.getFilmId())) {
            List<FilmView> filmViews = filmViewsRepository.findAll();
            if(filmViews != null) {
                List<String> filmIds = filmViews.stream().map(FilmView::getId).toList();
                if(filmIds.contains(command.getFilmId())) {
                    throw new FilmAlreadyCreatedException("Film with id " + command.getFilmId() + " already created.");
                }
            }
        }
    }

}
