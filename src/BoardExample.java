import dao.BoardService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import vo.Board;

public class BoardExample {

  private final BoardService boardService = new BoardService();
  private final Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    BoardExample boardEx = new BoardExample();
    boardEx.list();
  }

  private void list() {
    System.out.println("[게시글 목록]");
    System.out.println("-----------------------------------------------------------");
    System.out.printf("%-4s %-10s %-15s %-30s\n", "no", "writer", "date", "title");
    System.out.println("-----------------------------------------------------------");
    List<Board> boardList = boardService.getBoardList();
    for(Board board: boardList) {
      System.out.printf("%-4d %-10s %-15s %-30s\n", board.getBno(), board.getBwriter(), formatDate(board.getBdate()), board.getBtitle());
    }
    System.out.println("-----------------------------------------------------------");
    mainMenu();
  }

  private void mainMenu() {
    System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
    System.out.print("메뉴 선택: ");
    String menu = sc.nextLine();
    switch (menu) {
      case "1" -> create();
      case "2" -> read();
      case "3" -> clear();
      case "4" -> {
        System.out.println("*** 프로그램 종료 ***");
        return;
      }
      default -> {
        System.out.println("1~4 중에 입력해주세요.");
      }
    }
    list();
  }

  private void create() {
    System.out.println("[새 게시물 입력]");
    System.out.print("제목: ");
    String title = sc.nextLine();
    System.out.print("내용: ");
    String content = sc.nextLine();
    System.out.print("작성자: ");
    String writer = sc.nextLine();
    System.out.println("-----------------------------------------------------------");
    System.out.println("보조 메뉴: 1.OK | 2.Cancel");
    System.out.print("메뉴 선택: ");
    String menu = sc.nextLine();
    switch (menu) {
      case "1" -> {
        Board board = Board.builder()
            .btitle(title)
            .bcontent(content)
            .bwriter(writer)
            .bdate(LocalDate.now())
            .build();
        boardService.insertBoard(board);
      }
      case "2" -> {}
      default -> System.out.println("1,2 중에 입력해주세요.");
    }
  }

  private void read() {
    System.out.println("[게시물 읽기]");
    int findBno;
    System.out.print("bno: ");
    try{
      findBno = Integer.parseInt(sc.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("숫자를 입력해주세요.");
      return;
    }
    boardService.getBoardByBno(findBno)
        .ifPresentOrElse(
            board -> {
              printBoardDetail(board);
              System.out.println("보조 메뉴: 1.Update | 2.Delete | 3.List");
              System.out.print("메뉴 선택: ");
              String subMenu = sc.nextLine();
              switch (subMenu) {
                case "1" -> update(board);
                case "2" -> delete(board);
                case "3" -> {}
                default -> System.out.println("1~3 중에 입력해주세요.");
              }
            },
            () -> System.out.println("게시물이 존재하지 않습니다.")
        );
  }

  private void update(Board board) {
    System.out.println("[수정 내용 입력]");
    System.out.print("제목: ");
    String title = sc.nextLine();
    System.out.print("내용: ");
    String content = sc.nextLine();
    System.out.print("작성자: ");
    String writer = sc.nextLine();
    System.out.println("보조 메뉴: 1.OK | 2.Cancel");
    System.out.print("메뉴 선택: ");
    String menu = sc.nextLine();
    switch (menu) {
      case "1" -> {
        board.updateBoard(title, content, writer);
        boardService.updateBoard(board);
      }
      case "2" -> {}
      default -> System.out.println("1,2 중에 입력해주세요.");
    }
  }

  private void delete(Board board) {
    boardService.deleteBoard(board.getBno());
  }

  private void clear() {
    System.out.println("[게시물 전체 삭제]");
    System.out.println("-----------------------------------------------------------");
    System.out.println("보조 메뉴: 1.Ok | 2. Cancel");
    System.out.print("메뉴 선택: ");
    String menu = sc.nextLine();
    switch (menu) {
      case "1" -> {
        boardService.deleteAllBoard();
      }
      case "2" -> {}
      default -> System.out.println("1,2 중에 입력해주세요.");
    }
  }

  private void printBoardDetail(Board board) {
    System.out.println("############");
    System.out.print("번호: ");
    System.out.println(board.getBno());
    System.out.print("제목: ");
    System.out.println(board.getBtitle());
    System.out.print("내용: ");
    System.out.println(board.getBtitle());
    System.out.print("작성자: ");
    System.out.println(board.getBwriter());
    System.out.print("날짜: ");
    System.out.println(formatDate(board.getBdate()));
    System.out.println("############");
  }

  private String formatDate(LocalDate date) {
    return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
  }
}
