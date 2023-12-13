package project.pnu.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude={"commentList", "partyList"})
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	@Column(name="MEMBER_ID")
	private String id;
	private String pwd;
	private String name;	
	private String email;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.ROLE_MEMBER;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate = new Date();	
	private boolean enabled = true;
	@JsonManagedReference("defaultReference")
	@JsonIgnore
	@OneToMany(mappedBy="member", fetch=FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
	@Column(nullable=true)
	private List<Comment> commentList = new ArrayList<Comment>();
	@JsonIgnore
	@JsonManagedReference("defaultReference")
	@OneToMany(mappedBy="member", fetch=FetchType.EAGER,  cascade = CascadeType.ALL,orphanRemoval = true)
	@Column(nullable=true)
	private List<Party> partyList = new ArrayList<Party>();
}
