package sept.challenge2.account.model;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Integer ID;
    private String accountType;
    private String accountNo;
    private String accountName;
    private String accountBal;
    private String accountDate;


}
