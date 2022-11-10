package Instagram;

import Hadgehogs.SQL.ConnectionBuilder;
import Hadgehogs.baseCore.resourceReader;

import java.sql.*;

// Запрещенная в России, организация
public class Instagram {
    private Connection connectionSQL; // Кэшируем соединение

    public Connection getconnectionSQL() {
        if (connectionSQL == null) {
            try {
                connectionSQL = new ConnectionBuilder("postgres", "postgres", "1").build();
            } catch (SQLException e) {
                System.out.println("Не удалось получить соединение с SQL -сервером по причине: " + e.getMessage());
                return null;
            }

        }
        return connectionSQL;
    }

    public boolean dropTables() {
        Connection connectionSQL = getconnectionSQL();
        if (connectionSQL == null) {
            return false;
        }
        String query = resourceReader.readResourceTxtFile("dropTables.sql");
        try {
            Statement statement = connectionSQL.createStatement();
            boolean result = statement.execute(query);
            statement.close();
            return result;
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос SQL по причине: " + e.getMessage());
            return false;
        }
    }

    public boolean createTablesIfNecessary() {
        Connection connectionSQL = getconnectionSQL();
        if (connectionSQL == null) {
            return false;
        }
        String query = resourceReader.readResourceTxtFile("createTables.sql");
        try {
            Statement statement = connectionSQL.createStatement();
            boolean result = statement.execute(query);
            statement.close();
            return result;
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос SQL по причине: " + e.getMessage());
            return false;
        }
    }

    public int addUser(String userName, String password) {
        Connection connectionSQL = getconnectionSQL();
        if (connectionSQL == null) {
            return -1;
        }
        String query = resourceReader.readResourceTxtFile("addUser.sql");
        try {
            PreparedStatement preparedStatement = connectionSQL.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            int insertCount = preparedStatement.executeUpdate();
            if (insertCount < 1) {
                return -1;
            }
            query = resourceReader.readResourceTxtFile("getTableID.sql");
            query = query.replaceAll("%tablename%", "user");
            preparedStatement = connectionSQL.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            int userID = -1;
            if (resultSet.next()) {
                userID = resultSet.getInt("id");
            }
            resultSet.close();
            preparedStatement.close();
            return userID;

        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос SQL по причине: " + e.getMessage());
            return -1;
        }

    }

    public int addPost(String text, int user_id) {
        Connection connectionSQL = getconnectionSQL();
        if (connectionSQL == null) {
            return -1;
        }
        String query = resourceReader.readResourceTxtFile("addPost.sql");
        try {
            PreparedStatement preparedStatement = connectionSQL.prepareStatement(query);
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, user_id);

            int insertCount = preparedStatement.executeUpdate();
            if (insertCount < 1) {
                return -1;
            }
            query = resourceReader.readResourceTxtFile("getTableID.sql");
            query = query.replaceAll("%tablename%", "post");
            preparedStatement = connectionSQL.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            int currentID = -1;
            if (resultSet.next()) {
                currentID = resultSet.getInt("id");
            }
            resultSet.close();
            preparedStatement.close();
            return currentID;

        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос SQL по причине: " + e.getMessage());
            return -1;
        }

    }

    public int addComment(String text, int post_id, int user_id) {
        Connection connectionSQL = getconnectionSQL();
        if (connectionSQL == null) {
            return -1;
        }
        String query = resourceReader.readResourceTxtFile("addComment.sql");
        try {
            PreparedStatement preparedStatement = connectionSQL.prepareStatement(query);
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, post_id);
            preparedStatement.setInt(3, user_id);

            int insertCount = preparedStatement.executeUpdate();
            if (insertCount < 1) {
                return -1;
            }
            query = resourceReader.readResourceTxtFile("getTableID.sql");
            query = query.replaceAll("%tablename%", "comment");
            preparedStatement = connectionSQL.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            int currentID = -1;
            if (resultSet.next()) {
                currentID = resultSet.getInt("id");
            }
            resultSet.close();
            preparedStatement.close();
            return currentID;

        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос SQL по причине: " + e.getMessage());
            return -1;
        }

    }

    public int addLike(int user_id, int post_id, int comment_id) {
        Connection connectionSQL = getconnectionSQL();
        if (connectionSQL == null) {
            return -1;
        }
        String query = resourceReader.readResourceTxtFile("addLike.sql");
        try {
            PreparedStatement preparedStatement = connectionSQL.prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            if (post_id != 0) {
                preparedStatement.setInt(2, post_id);
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            if (comment_id != 0) {
                preparedStatement.setInt(3, comment_id);
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }

            int insertCount = preparedStatement.executeUpdate();
            if (insertCount < 1) {
                return -1;
            }
            query = resourceReader.readResourceTxtFile("getTableID.sql");
            query = query.replaceAll("%tablename%", "like");
            preparedStatement = connectionSQL.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            int currentID = -1;
            if (resultSet.next()) {
                currentID = resultSet.getInt("id");
            }
            resultSet.close();
            preparedStatement.close();
            return currentID;

        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос SQL по причине: " + e.getMessage());
            return -1;
        }

    }

    public boolean showStats() {
        Connection connectionSQL = getconnectionSQL();
        if (connectionSQL == null) {
            return false;
        }
        String query = resourceReader.readResourceTxtFileWithoutJoin("stats.sql");
        try {
            PreparedStatement preparedStatement = connectionSQL.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int user_Count = resultSet.getInt("user_Count");
                int post_Count = resultSet.getInt("post_Count");
                int comment_Count = resultSet.getInt("comment_Count");
                int like_Count = resultSet.getInt("like_Count");
                System.out.println("Общая статистика:");
                System.out.println(String.format("Всего: количество пользователей - (%s),  количество постов - (%s), количество комментариев - (%s), количество лайков - (%s)", String.valueOf(user_Count), String.valueOf(post_Count), String.valueOf(comment_Count), String.valueOf(like_Count)));
            }
            resultSet.close();
            preparedStatement.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос SQL по причине: " + e.getMessage());
            return false;
        }

    }

    public boolean showuserInfo(int user_id) {
        Connection connectionSQL = getconnectionSQL();
        if (connectionSQL == null) {
            return false;
        }
        String query = resourceReader.readResourceTxtFileWithoutJoin("userInfo.sql");
        try {
            PreparedStatement preparedStatement = connectionSQL.prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setInt(3, user_id); // Пока не разобрался, как вставлять одинаковый параметр
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Данные по пользователю ID:" + String.valueOf(user_id));
            if (resultSet.next()) {
                String userName = resultSet.getString("userName");
                Date created_at = resultSet.getDate("created_at");
                String firstPost = resultSet.getString("firstPost");
                int commentsCount = resultSet.getInt("commentsCount");
                System.out.println(String.format("Пользователь - (%s), Дата создания - (%s), Самый первый пост - (%s), Количество комментов - (%s)", (userName), String.valueOf(created_at), (firstPost), String.valueOf(commentsCount)));
            } else {
                System.out.println("Пользователь не найден");
            }
            resultSet.close();
            preparedStatement.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос SQL по причине: " + e.getMessage());
            return false;
        }

    }

}
