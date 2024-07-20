package service;

import java.util.List;
import java.util.Optional;
import domain.Board;

public interface BoardService {

  void insertBoard(Board board);

  void updateBoard(Board board);

  void deleteBoard(int bno);

  void deleteAllBoard();

  Optional<Board> getBoardByBno(int bno);

  List<Board> getBoardList();

}
