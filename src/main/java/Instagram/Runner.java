package Instagram;

public class Runner {
    public static void main(String[] args) {
        Instagram instagram = new Instagram();
        instagram.dropTables();
        instagram.createTablesIfNecessary();
        int userID = instagram.addUser("Ezhov", "128500");
        if (userID == -1) {
            return;
        }
        int postID = instagram.addPost("И изгнал Господь Адама из Рая небесного и поставил на Востоке Эдемского сада херувима - Меч обращающий, дабы защищать Древо Жизни. Ветхий Завет.", userID);
        if (postID == -1) {
            return;
        }
        postID = instagram.addPost("Страх человека, нигде в жизни не находящего себе места говорит, что этот человек всего ожидает от места, и ничего - от себя.", userID);
        if (postID == -1) {
            return;
        }
        userID = instagram.addUser("Hadgehogs", "128500");
        if (userID == -1) {
            return;
        }
        postID = instagram.addPost("И когда последняя крупица покидает песочные часы Времени, когда суета мирской жизни затихает, а ее неустанные и бесплодные метания подходят к концу, когда вокруг вас все замирает, будто на пороге Вечности, тогда сама Вечность задает каждому из миллионов миллионов лишь один вопрос -\n" +
                "\"Прожил ли ты свою жизнь в отчаянии?", userID);
        if (postID == -1) {
            return;
        }
        int commentID = instagram.addComment("Это Сёрен Кьеркегор. Это - классика, это - знать надо.", postID, userID);
        if (commentID == -1) {
            return;
        }
        postID = instagram.addPost("Человек – это верёвка, натянутая между зверем и сверхчеловеком. Верёвка над бездной. Опасный путь. Опасно идти вперёд. Опасно оглядываться назад. Опасно сомневаться и останавливаться. Величие человека в том, что он мост, а не тупик, что он постоянно ищет и развивается.", userID);
        if (postID == -1) {
            return;
        }

        commentID = instagram.addComment("Ницше жжет!", postID, userID);
        if (commentID == -1) {
            return;
        }

        int likeID = instagram.addLike(userID, postID, 0); //лайкнем пост
        likeID = instagram.addLike(userID, postID, 0); //лайкнем пост
        likeID = instagram.addLike(userID, 0, commentID); //лайкнем каммент
        instagram.showStats();
        instagram.showuserInfo(1);
        instagram.showuserInfo(2);
        instagram.showuserInfo(3);
    }
}
