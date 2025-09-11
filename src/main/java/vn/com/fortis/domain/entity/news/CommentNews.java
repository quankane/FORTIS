package vn.com.fortis.domain.entity.news;

import vn.com.fortis.domain.entity.BaseEntity;
import vn.com.fortis.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "comment_news")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentNews extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    String commentText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommentNews parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CommentNews> replies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    /* ========= Helper methods ========= */

    public void addReply(CommentNews reply) {
        replies.add(reply);
        reply.setParent(this);
    }

    public void removeReply(CommentNews reply) {
        replies.remove(reply);
        reply.setParent(null);
    }

}
