import exception.BoardException;
import message.Label;
import message.ErrorMessage;
import service.BoardService;
import service.BoardServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import domain.Board;
import service.BoardValidator;

public class BoardExample {

  private final BoardService boardServiceImpl = new BoardServiceImpl();
  private final Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    BoardExample boardEx = new BoardExample();
    boardEx.list();
  }

  private void list() {
    System.out.println(Label.LIST);
    System.out.println(Label.HORIZONTAL_LINE);
    System.out.printf("%-4s %-10s %-15s %-30s\n", "no", "writer", "date", "title");
    System.out.println(Label.HORIZONTAL_LINE);
    List<Board> boardList = boardServiceImpl.getBoardList();
    for(Board board: boardList) {
      System.out.printf("%-4d %-10s %-15s %-30s\n", board.getBno(), board.getBwriter(), formatDate(board.getBdate()), board.getBtitle());
    }
    System.out.println(Label.HORIZONTAL_LINE);
    mainMenu();
  }

  private void mainMenu() {
    System.out.print(Label.MAIN_MENU);
    System.out.println("1.Create | 2.Read | 3.Clear | 4.Exit");
    System.out.print(Label.MENU_SELECT);
    String menu = sc.nextLine();
    switch (menu) {
      case "1" -> create();
      case "2" -> read();
      case "3" -> clear();
      case "4" -> {
        System.out.println(Label.EXIT_MASSAGE);
        return;
      }
      default -> {
        System.out.println("1~4 중에 입력해주세요.");
      }
    }
    list();
  }

  private void create() {
    System.out.println(Label.CREATE);
    Board board;
    while(true) {
      try {
        System.out.print(Label.TITLE);
        String title = sc.nextLine();
        if(!BoardValidator.validateTitle(title)) {
          throw new BoardException(ErrorMessage.INVALID_TITLE);
        }
        System.out.print(Label.CONTENT);
        String content = sc.nextLine();
        if(!BoardValidator.validateContent(content)){
          throw new BoardException(ErrorMessage.INVALID_CONTENT);
        }
        System.out.print(Label.WRITER);
        String writer = sc.nextLine();
        if(!BoardValidator.validateWriter(writer)) {
          throw new BoardException(ErrorMessage.INVALID_WRITER);
        }
        board = Board.builder()
                .btitle(title)
                .bcontent(content)
                .bwriter(writer)
                .bdate(LocalDate.now())
                .build();
        break;
      } catch (BoardException e) {
        System.out.println(e.getMessage());
      }
    }
    System.out.println(Label.HORIZONTAL_LINE);
    System.out.print(Label.SUB_MENU);
    System.out.println("1.OK | 2.Cancel");
    System.out.print(Label.MENU_SELECT);
    String menu = sc.nextLine();
    switch (menu) {
      case "1" -> {
        boardServiceImpl.insertBoard(board);
      }
      case "2" -> {}
      default -> System.out.println("1,2 중에 입력해주세요.");
    }
  }

  private void read() {
    System.out.println(Label.READ);
    System.out.print(Label.BNO);
    String bno = sc.nextLine();
    try{
      if(!BoardValidator.validateBno(bno)) {
        throw new BoardException(ErrorMessage.INVALID_BNO);
      }
      int findBno = Integer.parseInt(bno);
      Board board = boardServiceImpl.getBoardByBno(findBno)
              .orElseThrow(() -> new BoardException(ErrorMessage.NOT_EXISTS_BOARD));
      printBoardDetail(board);
      System.out.print(Label.SUB_MENU);
      System.out.println("1.Update | 2.Delete | 3.List");
      System.out.print(Label.MENU_SELECT);
      String subMenu = sc.nextLine();
      switch (subMenu) {
        case "1" -> update(board);
        case "2" -> delete(board);
        case "3" -> {}
        default -> System.out.println("1~3 중에 입력해주세요.");
      }
    } catch(BoardException e) {
      System.out.println(e.getMessage());
    }
  }

  private void update(Board board) {
    System.out.println(Label.UPDATE);
    while(true) {
      try {
        System.out.print(Label.TITLE);
        String title = sc.nextLine();
        if(!BoardValidator.validateTitle(title)) {
          throw new BoardException(ErrorMessage.INVALID_TITLE);
        }
        System.out.print(Label.CONTENT);
        String content = sc.nextLine();
        if(!BoardValidator.validateContent(content)){
          throw new BoardException(ErrorMessage.INVALID_CONTENT);
        }
        System.out.print(Label.WRITER);
        String writer = sc.nextLine();
        if(!BoardValidator.validateWriter(writer)) {
          throw new BoardException(ErrorMessage.INVALID_WRITER);
        }
        board.updateBoard(title, content, writer);
        break;
      } catch (BoardException e) {
        System.out.println(e.getMessage());
      }
    }
    System.out.print(Label.SUB_MENU);
    System.out.println("1.OK | 2.Cancel");
    System.out.print(Label.MENU_SELECT);
    String menu = sc.nextLine();
    switch (menu) {
      case "1" -> {
        boardServiceImpl.updateBoard(board);
      }
      case "2" -> {}
      default -> System.out.println("1,2 중에 입력해주세요.");
    }
  }

  private void delete(Board board) {
    boardServiceImpl.deleteBoard(board.getBno());
  }

  private void clear() {
    System.out.println(Label.CLEAR);
    System.out.println(Label.HORIZONTAL_LINE);
    System.out.print(Label.SUB_MENU);
    System.out.println("1.OK | 2.Cancel");
    System.out.print(Label.MENU_SELECT);
    String menu = sc.nextLine();
    switch (menu) {
      case "1" -> {
        boardServiceImpl.deleteAllBoard();
      }
      case "2" -> {}
      default -> System.out.println("1,2 중에 입력해주세요.");
    }
  }

  private void printBoardDetail(Board board) {
    System.out.println(Label.HORIZONTAL_SHARP);
    System.out.print(Label.BNO);
    System.out.println(board.getBno());
    System.out.print(Label.TITLE);
    System.out.println(board.getBtitle());
    System.out.print(Label.WRITER);
    System.out.println(board.getBwriter());
    System.out.print(Label.CONTENT);
    System.out.println(board.getBcontent());
    System.out.print(Label.DATE);
    System.out.println(formatDate(board.getBdate()));
    System.out.println(Label.HORIZONTAL_SHARP);
  }

  private String formatDate(LocalDate date) {
    return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
  }
}
