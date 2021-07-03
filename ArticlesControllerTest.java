package fullforum.controllers;

import fullforum.controllers.ArticlesController;
import fullforum.data.models.Article;
import fullforum.data.models.User;
import fullforum.dependency.FakeAuth;
import fullforum.BaseTest;
import fullforum.data.repos.ArticleRepository;
import fullforum.data.repos.UserRepository;
import fullforum.dto.in.CreateArticleModel;
import fullforum.dto.in.PatchArticleModel;
import fullforum.dto.out.QArticle;
import fullforum.dto.out.Quser;
import fullforum.errhand.ForbidException;
import fullforum.errhand.NotFoundException;
import fullforum.errhand.UnauthorizedException;
import fullforum.services.Snowflake;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 在一般的测试中，mockMvc只是少量使用，更多的时候是直接调用controller的方法。
 * 它们的区别是：mockMvc能够覆盖更广的东西，例如路由参数、查询参数的annotation标记是否到位
 */
public class ArticlesControllerTest extends BaseTest {
    @Autowired
    ArticlesController articlesController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    FakeAuth auth;

    @Autowired
    MockMvc mockMvc;


    //region createArticle test

//    @Test
//    void createArticle_throw_unauthorized_when_not_log_in() {
//        var model = new CreateArticleModel("asd", "asdaa");
//        assertThrows(UnauthorizedException.class, () -> articlesController.createArticle(model));
//    }
//
//    @Test
//    void createArticle_succeed_and_insert_to_db_when_all_ok() {
//        // Arrange
//        auth.setRealUserId(1);
//        var model = new CreateArticleModel("asd", "asdaa");
//
//        // Act
//        var idDto = articlesController.createArticle(model);
//        var articleId = idDto.id;
//
//        // Assert
//        var article = articleRepository.getOne(articleId);
//
//        assertThat(article.getId()).isEqualTo(articleId);
//        assertThat(article.getTitle()).isEqualTo(model.title);
//        assertThat(article.getText()).isEqualTo(model.text);
//    }
//
//    //endregion
//
//
//    //region removeArticle test
//
//    @Test
//    void removeArticle_throw_unauthorized_when_not_login() {
//        assertThrows(UnauthorizedException.class, () -> articlesController.removeArticle(1));
//    }
//
//    @Test
//    void removeArticle_throw_notfound_when_article_not_exist() {
//        // Arrange
//        auth.setRealUserId(1);
//
//        // Act & Assert
//        assertThrows(NotFoundException.class, () -> articlesController.removeArticle(1));
//    }
//
//    @Test
//    void removeArticle_throw_forbid_when_article_not_belong_to_current_user() {
//        // Arrange
//        auth.setRealUserId(1);
//        var article = new Article(1, "asd", "aaaaa", 2);
//        articleRepository.save(article);
//
//        // Act & Assert
//        assertThrows(ForbidException.class, () -> articlesController.removeArticle(1));
//    }
//
//    @Test
//    void removeArticle_succeed_and_change_db_when_all_ok() {
//        // Arrange
//        auth.setRealUserId(1);
//        var article = new Article(1, "asd", "aaaaa", 1);
//        articleRepository.save(article);
//
//        // Act
//        articlesController.removeArticle(1);
//
//        // Assert
//        var articleInDb = articleRepository.findById(1L).orElse(null);
//        assertThat(articleInDb).isNull();
//    }
//
//    //endregion
//
//
//    //region patchArticle test
//
//    @Test
//    void patchArticle_throw_unauthorized_when_not_login() {
//        var model = new PatchArticleModel("aaa", "bbbb");
//        assertThrows(UnauthorizedException.class, () -> articlesController.patchArticle(1, model));
//    }
//
//    @Test
//    void patchArticle_throw_not_found_when_article_not_exist() {
//        auth.setRealUserId(1);
//
//        var model = new PatchArticleModel("aaa", "bbbb");
//        assertThrows(NotFoundException.class, () -> articlesController.patchArticle(1, model));
//    }
//
//    @Test
//    void patchArticle_throw_forbid_when_article_not_belong_to_current_user() {
//        // Arrange
//        auth.setRealUserId(1);
//        var article = new Article(1, "aaa", "ccccc", 2);
//        articleRepository.save(article);
//
//        // Act & Assert
//        var model = new PatchArticleModel("aaa", "bbbb");
//        assertThrows(ForbidException.class, () -> articlesController.patchArticle(1, model));
//
//    }
//
//    @Test
//    void patchArticle_succeed_when_all_ok_and_update_db() {
//        // Arrange
//        auth.setRealUserId(1);
//        var article = new Article(1, "aaa", "ccccc", 1);
//        articleRepository.save(article);
//
//        // Act
//        var model = new PatchArticleModel("1111", "2222");
//        articlesController.patchArticle(1, model);
//
//        // Assert
//        var articleInDb = articleRepository.getOne(article.getId());
//        assertThat(articleInDb.getTitle()).isEqualTo(model.title);
//        assertThat(articleInDb.getText()).isEqualTo(model.text);
//    }
//
//    //endregion
//
//
//    // region getArticleById test
//
//    @Test
//    void getArticleById_return_article_info_when_article_exist() {
//        // Arrange
//        var article = new Article(1, "aaaa", "bsbdbbdbdbd", 1);
//        articleRepository.save(article);
//
//        // Act
//        var articleInfo = articlesController.getArticleById(1);
//        assertThat(articleInfo.id).isEqualTo(article.getId());
//        assertThat(articleInfo.title).isEqualTo(article.getTitle());
//        assertThat(articleInfo.text).isEqualTo(article.getText());
//        assertThat(articleInfo.userId).isEqualTo(article.getUserId());
//    }
//
//    // endregion
//
//
//    // region getArticlesTest
//
//    @Autowired
//    Snowflake snowflake;
//
//    @Test
//    void getArticles_simple_test() {
//        // Arrange
//        var a = new User(snowflake.nextId(), "aaaa", "asdadsada");
//        var b = new User(snowflake.nextId(), "bbbb", "adddddddddss");
//        userRepository.saveAll(Arrays.asList(a, b));
//
//        var a1 = new Article(snowflake.nextId(), "asdasd", "ad", a.getId());
//        var a2 = new Article(snowflake.nextId(), "ooooo", "ad", a.getId());
//        var b1 = new Article(snowflake.nextId(), "sdsd", "das", b.getId());
//        articleRepository.saveAll(Arrays.asList(a1, a2, b1));
//
//        // all articles of user A
//        var articles = articlesController.getArticles(a.getId(), null, 0, 10);
//        assertThat(articles).hasSize(2);
//
//        var articleA1 = articles.stream().filter(p -> p.id == a1.getId())
//                .findFirst().orElse(null);
//        assertQArticleConsistsWith(articleA1, a1, a);
//
//        var articleA2 = articles.stream().filter(p -> p.id == a2.getId())
//                .findFirst().orElse(null);
//        assertQArticleConsistsWith(articleA2, a2, a);
//
//        // user A and title keyword = 'ooo'
//        articles = articlesController.getArticles(a.getId(), "ooo", 0, 10);
//        assertThat(articles).hasSize(1);
//        var ar0 = articles.get(0);
//        assertQArticleConsistsWith(ar0, a2, a);
//    }
//
//    @Autowired
//    ModelMapper modelMapper;
//
//    void assertQArticleConsistsWith(QArticle q, Article article, User user) {
//        // TODO: QUser.convert should be tested
//        // TODO: QArticle.convert should be tested
//        assertThat(q).usingRecursiveComparison()
//                .isEqualTo(QArticle.convert(article, Quser.convert(user, modelMapper), modelMapper));
//
//    }

    // endregion

    @Test
    void createArticle_should_throw_ununthorizedException_if_user_not_login() {
        var model = new CreateArticleModel("titletitle", "texttext");

        assertThrows(UnauthorizedException.class, () -> articlesController.createArticle(model));
    }

    @Test
    void createArticle_should_return_correct_info_if_create_successfully() {
        auth.setRealUserId(1);
        var model = new CreateArticleModel("titletitle", "texttext");


        // Act
        var idDTO = articlesController.createArticle(model);

        //assert
        var article = articleRepository.getOne(idDTO.id);
        assertThat(article.getTitle()).isEqualTo("titletitle");
        assertThat(article.getText()).isEqualTo("texttext");

    }

    @Test
    void removeArticle_should_throw_unAuthorizedException_is_not_login() {
        assertThrows(UnauthorizedException.class, () -> articlesController.removeArticle(1));
    }

    @Test
    void removeArtcile_should_throw_ForbidException_if_user_is_not_owner() {
        //Prepare
        auth.setRealUserId(1);
        var article = new Article(1, "title" ,"text", 2);
        articleRepository.save(article);

        assertThrows(ForbidException.class, () -> articlesController.removeArticle(1));
    }

    @Test
    void removeArtcile_should_throw_NotFoundException_if_article_no_exist() {
        auth.setRealUserId(1);

        assertThrows(NotFoundException.class, () -> articlesController.removeArticle(1));
    }

    @Test
    void removeArtcile_should_succeed_if_all_is_well() {
        auth.setRealUserId(1);
        var article = new Article(1, "title" ,"text", 1);
        articleRepository.save(article);

        articlesController.removeArticle(1);

        assertThat(articlesController.getArticleById(1)).isEqualTo(null);
    }

    @Test
    void patchArticle_should_throw_unauthorizedException_if_not_login() {
        //Prepare
        var model = new PatchArticleModel("new_title", "new_text");

        //assert
        assertThrows(UnauthorizedException.class, () -> articlesController.patchArticle(1, model));
    }

    @Test
    void patchArticle_should_throw_FobidException_if_not_owner() {
        //Prepare
        auth.setRealUserId(1);
        var article = new Article(1, "title" ,"text", 2);
        var new_article = new PatchArticleModel("new_title", "new_text");
        articleRepository.save(article);

        //assert
        assertThrows(ForbidException.class, () -> articlesController.patchArticle(1, new_article));
    }

    @Test
    void patchArticle_should_throw_NotFoundException_if_article_not_exist() {
        //Prepare
        auth.setRealUserId(1);
        var article = new Article(1, "title" ,"text", 1);
        var new_article = new PatchArticleModel("new_title", "new_text");
        articleRepository.save(article);

        //assert
        assertThrows(NotFoundException.class, () -> articlesController.patchArticle(2, new_article));
    }

    @Test
    void patchArticle_should_update_sucessful_if_all_is_well() {
        //Prepare
        auth.setRealUserId(1);
        var article = new Article(1, "title" ,"text", 1);
        var new_article = new PatchArticleModel("new_title", "new_text");
        articleRepository.save(article);

        //Act
        articlesController.patchArticle(1, new_article);

        //assert
        var updatedArticle = articleRepository.getOne((long) 1);
        assertThat(updatedArticle.getText()).isEqualTo("new_text");
        assertThat(updatedArticle.getTitle()).isEqualTo("new_title");
    }

    @Test
    void getArticleById_should_return_null_if_article_not_exist() {
        var article = new Article(1, "title", "text", 1);
        articleRepository.save(article);

        var result = articlesController.getArticleById(2);

        assertThat(result).isEqualTo(null);
    }

    @Test
    void getArticleById_should_return_right_article_if_all_is_well() {
        var article = new Article(1, "title", "text", 1);
        articleRepository.save(article);

        var result = articlesController.getArticleById(1);

        assertThat(result.getText()).isEqualTo("text");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getUserId()).isEqualTo(1);

    }
}