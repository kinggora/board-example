package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import common.ObjectIO;
import domain.Board;

public class BoardServiceImpl extends ObjectIO implements BoardService {

  @Override
  public void insertBoard(Board board) {
    String query = "INSERT INTO board(btitle, bcontent, bwriter, bdate) VALUES (?,?,?,?)";
    Map<Integer, Object> paramMap = new HashMap<>();
    paramMap.put(1, board.getBtitle());
    paramMap.put(2, board.getBcontent());
    paramMap.put(3, board.getBwriter());
    paramMap.put(4, board.getBdate());
    super.execute(query, paramMap);
  }

  @Override
  public void updateBoard(Board board) {
    String query = "UPDATE board SET btitle = ?, bcontent = ?, bwriter = ? WHERE bno = ?";
    Map<Integer, Object> paramMap = new HashMap<>();
    paramMap.put(1, board.getBtitle());
    paramMap.put(2, board.getBcontent());
    paramMap.put(3, board.getBwriter());
    paramMap.put(4, board.getBno());
    super.execute(query, paramMap);
  }

  @Override
  public void deleteBoard(int bno) {
    String query = "DELETE FROM board WHERE bno = ?";
    Map<Integer, Object> paramMap = new HashMap<>();
    paramMap.put(1, bno);
    super.execute(query, paramMap);
  }

  @Override
  public void deleteAllBoard() {
    String query = "DELETE FROM board";
    super.execute(query);
  }

  @Override
  public List<Board> getBoardList() {
    String query = "SELECT * FROM board";
    ResultSet rs = null;
    List<Board> boardList = new ArrayList<>();
    try {
      rs = super.execute(query, rs);
      if (rs == null) {
        return boardList;
      }
      while (rs.next()) {
        Board board = Board.builder()
            .bno(rs.getInt("bno"))
            .btitle(rs.getString("btitle"))
            .bcontent(rs.getString("bcontent"))
            .bwriter(rs.getString("bwriter"))
            .bdate(rs.getDate("bdate").toLocalDate())
            .build();
        boardList.add(board);
      }
      rs.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      super.close();
    }
    return boardList;
  }

  @Override
  public Optional<Board> getBoardByBno(int findBno) {
    String query = "SELECT * FROM board WHERE bno = ?";
    Map<Integer, Object> paramMap = new HashMap<>();
    paramMap.put(1, findBno);

    ResultSet rs = null;
    try {
      rs = super.execute(query, paramMap, rs);
      if (rs == null) {
        return Optional.empty();
      }
      Board board = null;
      if (rs.next()) {
         board = Board.builder()
            .bno(rs.getInt("bno"))
            .btitle(rs.getString("btitle"))
            .bcontent(rs.getString("bcontent"))
            .bwriter(rs.getString("bwriter"))
            .bdate(rs.getDate("bdate").toLocalDate())
            .build();
      }
      rs.close();
      return Optional.ofNullable(board);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      super.close();
    }
  }

}
