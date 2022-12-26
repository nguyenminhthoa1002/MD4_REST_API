package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userName;
    @JsonIgnore
    private String password;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String address;
    @JsonFormat(pattern = "dd/MM/yyyy")
//    private Date userCreateDate;
    private LocalDateTime userCreateDate;
    private boolean userStatus;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "user_role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Roles> listRoles = new HashSet<>();
}
