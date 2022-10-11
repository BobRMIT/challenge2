package sept.challenge2.account.model;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Integer ID;
    private String AccountType;
    private String AccountNo;
    private String AccountName;
    private String AccountBal;
    private String AccountDate;


}
