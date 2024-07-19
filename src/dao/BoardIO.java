package dao;

import java.util.List;
import java.util.Optional;
import vo.Board;

public interface BoardIO {

  void insertBoard(Board board);

  void updateBoard(Board board);

  void deleteBoard(int bno);

  void deleteAllBoard();

  Optional<Board> getBoardByBno(int bno);

  List<Board> getBoardList();

}
