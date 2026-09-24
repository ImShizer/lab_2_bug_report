package ua.opnu.labwork2.reader.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.opnu.labwork2.loan.persistence.Loan;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "readers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private List<Loan> loans;

    public Long getId() {
        return id;
    }
}