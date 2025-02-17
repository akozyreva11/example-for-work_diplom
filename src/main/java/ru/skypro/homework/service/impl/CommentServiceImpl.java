package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.dto.mapper.CommentMapper;
import ru.skypro.homework.error.EntityModelNotFoundException;
import ru.skypro.homework.error.ExceptionControllerAdvice;
import ru.skypro.homework.model.AdModel;
import ru.skypro.homework.model.CommentModel;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.repository.CommentModelRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    private final CommentModelRepository commentModelRepository;
    private final AdService adService;
    private final CommentMapper mapper;
    private final UserService userService;

    public CommentServiceImpl(CommentModelRepository commentModelRepository, AdService adService, CommentMapper mapper, UserService userService) {
        this.commentModelRepository = commentModelRepository;
        this.adService = adService;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public CommentModel createComment(int adId, CreateOrUpdateCommentDTO properties, String username) throws EntityModelNotFoundException {
        CommentModel commentModel = new CommentModel();
        UserModel userModel = userService.findUserByUserName(username);
        mapper.mapCreateOrUpdateCommentDTOToCommentModel(commentModel, properties);
        commentModel.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        commentModel.setAd(adService.findAdById(adId));
        commentModel.setUser(userModel);
        commentModelRepository.save(commentModel);
        return commentModel;
    }

    @Override
    public CommentModel findById(int id) throws EntityModelNotFoundException {
        return commentModelRepository.findById(id).orElseThrow(() -> new EntityModelNotFoundException("CommentModel with id" +
                " = " + id + " not found"));
    }

    @Override
    public CommentModel updateComment(int id, CreateOrUpdateCommentDTO properties) throws EntityModelNotFoundException {
        CommentModel existingComment = findById(id);
        mapper.mapCreateOrUpdateCommentDTOToCommentModel(existingComment, properties);
        existingComment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.info("Comment with id = " + id + " updated with following value: {}", properties);
        commentModelRepository.save(existingComment);
        return existingComment;
    }

    @Override
    public void deleteComment(int id) throws EntityModelNotFoundException {
        CommentModel existingComment = findById(id);
        commentModelRepository.delete(existingComment);
        log.info("Comment with id = " + id + " successfully deleted from DB");
    }

    @Override
    public List<CommentModel> getAllComments(int adId) throws EntityModelNotFoundException {
        AdModel adModel = adService.findAdById(adId);
        return adModel.getComments();
    }
}