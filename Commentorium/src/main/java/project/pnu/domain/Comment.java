package project.pnu.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = { "member" })
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	@Column(nullable=false)
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate = new Date();
	private Long boardId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false)
	@JsonBackReference("defaultReference")
	private Member member;

	@Override
	public String toString() {
		return "Comment{" + "seq=" + seq + ", content='" + content + '\'' + ", createDate=" + createDate + "boardId=" + boardId +
		// Exclude the 'member' field from the toString representation
				'}';
	}

	@Transient
	private String memId;
}
