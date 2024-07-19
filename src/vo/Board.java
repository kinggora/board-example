package vo;

import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Board {

  private Integer bno;
  private String btitle;
  private String bcontent;
  private String bwriter;
  private LocalDate bdate;

  public void updateBoard(String title, String content, String writer) {
    this.btitle = title;
    this.bcontent = content;
    this.bwriter = writer;
  }
}
