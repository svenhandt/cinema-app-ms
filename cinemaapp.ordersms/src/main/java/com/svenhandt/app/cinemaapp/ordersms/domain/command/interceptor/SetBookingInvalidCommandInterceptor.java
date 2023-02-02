package com.svenhandt.app.cinemaapp.ordersms.domain.command.interceptor;

import com.svenhandt.app.cinemaapp.ordersms.domain.command.interceptor.exception.BookingAlreadyInvalidException;
import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.SetBookingInvalidCommand;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.BookingView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.repository.BookingViewsRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class SetBookingInvalidCommandInterceptor  implements MessageDispatchInterceptor<CommandMessage<?>> {

    private BookingViewsRepository bookingViewsRepository;

    public SetBookingInvalidCommandInterceptor(BookingViewsRepository bookingViewsRepository) {
        this.bookingViewsRepository = bookingViewsRepository;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            if(SetBookingInvalidCommand.class.equals(command.getPayloadType())) {
                SetBookingInvalidCommand setBookingInvalidCommand = (SetBookingInvalidCommand) command.getPayload();
                checkBookingAlreadyInvalid(setBookingInvalidCommand);
            }
            return command;
        };
    }

    private void checkBookingAlreadyInvalid(SetBookingInvalidCommand command) {
        String bookingId = command.getBookingId();
        BookingView bookingView = bookingViewsRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalStateException("Booking with id " + bookingId + " not found!"));
        if(!bookingView.isValid()) {
            throw new BookingAlreadyInvalidException("Booking with id " + bookingId + " already invalid.");
        }
    }

}
