package com.arounders.web.service;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.dto.criteria.BoardCriteria;
import com.arounders.web.entity.Board;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BoardService {

    List<BoardDTO> getHiddenList();

    List<BoardDTO> getList(BoardCriteria criteria);

    BoardDTO getBoard(Long id);

    Long createBoard(BoardDTO boardDTO);

    Long editBoard(BoardDTO boardDTO);

    Long removeBoard(Long id);

    /* About Mypage */
    List<BoardDTO> getMyList(BoardCriteria criteria, Long memberId);

    Map<String, Integer> getCountListByCategory(Long memberId);

    default Board dtoToEntity(BoardDTO dto){

        return Board.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .memberId(dto.getMemberId())
                .categoryId(dto.getCategoryId())
                .isHidden(dto.getIsHidden())
                .region(dto.getRegion())
                .thumbnailName(dto.getThumbnailName())
                .thumbnailPath(dto.getThumbnailPath()).build();
    }
}
