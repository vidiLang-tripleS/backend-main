package TripleS.VidiLang.folder.entity;

import TripleS.VidiLang.member.entity.Member;
import TripleS.VidiLang.member.entity.SocialType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static TripleS.VidiLang.member.TestMember.createDefaultMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FolderTest {

    private final Member member = createDefaultMember();

    @Nested
    @DisplayName("Folder 생성 테스트")
    class CreateFolderTest {

        @Test
        @DisplayName("정상적인 Folder 생성 테스트")
        void should_CreateFolder_When_ValidArguments() {
            Folder folder = Folder.builder()
                    .member(member)
                    .name("My Folder")
                    .colorType(ColorType.BLUE)
                    .languageType(LanguageType.ENGLISH)
                    .build();

            assertThat(folder).isNotNull();
            assertThat(folder.getMember()).isEqualTo(member);
            assertThat(folder.getName()).isEqualTo("My Folder");
            assertThat(folder.getColorType()).isEqualTo(ColorType.BLUE);
            assertThat(folder.getLanguageType()).isEqualTo(LanguageType.ENGLISH);
        }

        @Test
        @DisplayName("폴더 소유자가 null이면 예외 발생")
        void should_ThrowException_When_MemberIsNull() {
            assertThatThrownBy(() -> Folder.builder()
                    .member(null)
                    .name("My Folder")
                    .colorType(ColorType.BLUE)
                    .languageType(LanguageType.ENGLISH)
                    .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("폴더 소유자는 null일 수 없습니다.");
        }

        @Test
        @DisplayName("폴더 이름이 null 또는 공백이면 예외 발생")
        void should_ThrowException_When_NameIsEmpty() {
            assertThatThrownBy(() -> Folder.builder()
                    .member(member)
                    .name("")
                    .colorType(ColorType.BLUE)
                    .languageType(LanguageType.ENGLISH)
                    .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("폴더 이름은 필수입니다.");
        }

        @Test
        @DisplayName("색상 타입이 null이면 예외 발생")
        void should_ThrowException_When_ColorTypeIsNull() {
            assertThatThrownBy(() -> Folder.builder()
                    .member(member)
                    .name("My Folder")
                    .colorType(null)
                    .languageType(LanguageType.ENGLISH)
                    .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("색상 타입은 필수입니다.");
        }

        @Test
        @DisplayName("언어 타입이 null이면 예외 발생")
        void should_ThrowException_When_LanguageTypeIsNull() {
            assertThatThrownBy(() -> Folder.builder()
                    .member(member)
                    .name("My Folder")
                    .colorType(ColorType.BLUE)
                    .languageType(null)
                    .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("언어 타입은 필수입니다.");
        }
    }

    @Nested
    @DisplayName("Folder 업데이트 테스트")
    class UpdateFolderTest {

        private final Folder folder = Folder.builder()
                .member(member)
                .name("My Folder")
                .colorType(ColorType.BLUE)
                .languageType(LanguageType.ENGLISH)
                .build();

        @Test
        @DisplayName("정상적인 업데이트 수행")
        void should_UpdateFolder_When_ValidArguments() {
            folder.update("New Folder", ColorType.RED, LanguageType.ENGLISH);

            assertThat(folder.getName()).isEqualTo("New Folder");
            assertThat(folder.getColorType()).isEqualTo(ColorType.RED);
            assertThat(folder.getLanguageType()).isEqualTo(LanguageType.ENGLISH);
        }

        @Test
        @DisplayName("같은 값으로 업데이트 시 변경되지 않음")
        void should_NotUpdate_When_SameValueProvided() {
            folder.update("My Folder", ColorType.BLUE, LanguageType.ENGLISH);

            assertThat(folder.getName()).isEqualTo("My Folder");
            assertThat(folder.getColorType()).isEqualTo(ColorType.BLUE);
            assertThat(folder.getLanguageType()).isEqualTo(LanguageType.ENGLISH);
        }

        @Test
        @DisplayName("업데이트 시 폴더 이름이 null이면 예외 발생")
        void should_ThrowException_When_UpdateNameIsNull() {
            assertThatThrownBy(() -> folder.update(null, ColorType.RED, LanguageType.ENGLISH))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("폴더 이름은 필수입니다.");
        }

        @Test
        @DisplayName("업데이트 시 폴더 이름이 공백이면 예외 발생")
        void should_ThrowException_When_UpdateNameIsEmpty() {
            assertThatThrownBy(() -> folder.update("", ColorType.RED, LanguageType.ENGLISH))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("폴더 이름은 필수입니다.");
        }

        @Test
        @DisplayName("업데이트 시 색상 타입이 null이면 예외 발생")
        void should_ThrowException_When_UpdateColorTypeIsNull() {
            assertThatThrownBy(() -> folder.update("New Folder", null, LanguageType.ENGLISH))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("색상 타입은 필수입니다.");
        }

        @Test
        @DisplayName("업데이트 시 언어 타입이 null이면 예외 발생")
        void should_ThrowException_When_UpdateLanguageTypeIsNull() {
            assertThatThrownBy(() -> folder.update("New Folder", ColorType.RED, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("언어 타입은 필수입니다.");
        }
    }
}