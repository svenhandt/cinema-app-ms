package com.svenhandt.app.cinemaapp.roomsms.application.service.impl;

import com.svenhandt.app.cinemaapp.roomsms.application.service.InitializationService;
import com.svenhandt.app.cinemaapp.roomsms.domain.command.interceptor.exception.RoomAlreadyCreatedException;
import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.CreateRoomCommand;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InitializationServiceImpl implements InitializationService {

    private static final Logger LOG = LoggerFactory.getLogger(InitializationServiceImpl.class);

    @Value("${initfiles.rooms.dir.path}")
    private String initRoomsFilesDirPath;

    @Value("${initfiles.rooms.seat.symbol}")
    private String initFilesSeatSymbol;

    private final CommandGateway commandGateway;

    public InitializationServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

    @Override
    public void initialize() {
        Set<String> fileNames = listFilesUsingFileWalk();
        if (fileNames != null) {
            for (String fileName : fileNames) {
                createRoom(fileName);
            }
        }
    }

    private Set<String> listFilesUsingFileWalk() {
        Set<String> fileNames;
        Stream<Path> stream = null;
        try {
            URL fileUrl = getClass().getClassLoader().getResource(initRoomsFilesDirPath);
            URI fileUri = fileUrl.toURI();
            stream = Files.walk(Paths.get(fileUri));
            fileNames = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        } catch (URISyntaxException | IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return fileNames;
    }

    private void createRoom(String fileName) {
        try {
            CreateRoomCommand command = CreateRoomCommand
                    .builder()
                    .targetId(fileName.replaceAll(".txt", StringUtils.EMPTY))
                    .fileName(fileName)
                    .initRoomsFilesDirPath(initRoomsFilesDirPath)
                    .seatSymbol(initFilesSeatSymbol)
                    .build();
            commandGateway.sendAndWait(command);
        } catch (RoomAlreadyCreatedException ex) {
            LOG.warn("Room already created: " + fileName);
        }
    }

}
