package sept.challenge2.person.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    
    private Integer ID;
    private String name;
    private String address;
    private Integer postcode;
    private Integer age;
    private String job;
    private String email;
    private String phoneno;

}
