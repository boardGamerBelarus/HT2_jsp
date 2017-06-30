package app;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.DBWorker;

public class Person implements Serializable {

	// Данные записи о человеке.
	private String id = "";
	private String name = "";
	private String surname = "";
	private String middlename = "";
	private HashMap<String, String> phones = new HashMap<String, String>();

	// Конструктор для создания записи о человеке на основе данных из БД.
	public Person(String id, String name, String surname, String middlename) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.middlename = middlename;

		// Извлечение телефонов человека из БД, запись их в карту которую можно
		// будет достать через объект Person.
		ResultSet db_data = DBWorker.getInstance().getDBData("SELECT * FROM `phone` WHERE `owner`=" + id);

		try {
			// Если у человека нет телефонов, ResultSet будет == null.
			if (db_data != null) {
				while (db_data.next()) {
					this.phones.put(db_data.getString("id"), db_data.getString("number"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Конструктор для создания пустой записи о человеке.
	public Person() {
		this.id = "0";
		this.name = "";
		this.surname = "";
		this.middlename = "";
	}

	// Конструктор для создания записи, предназначенной для добавления в БД.
	public Person(String name, String surname, String middlename) {
		this.id = "0";
		this.name = name;
		this.surname = surname;
		this.middlename = middlename;
	}

	// Валидация частей ФИО. Для отчества можно передать второй параетр == true,
	// тогда допускается пустое значение.
	public boolean validateFMLNamePart(String fml_name_part, boolean empty_allowed) {
		if (empty_allowed) {
			Matcher matcher = Pattern.compile("[\\w-]{0,150}", Pattern.UNICODE_CHARACTER_CLASS).matcher(fml_name_part);
			return matcher.matches();
		} else {
				Matcher matcher = Pattern.compile("[\\w-]{1,150}", Pattern.UNICODE_CHARACTER_CLASS).matcher(fml_name_part);
				return matcher.matches();
		}

	}

	public boolean addPhone(String phone) {
		DBWorker db = DBWorker.getInstance();

		String query;

		query = "INSERT INTO `phone` (`owner`, `number`) VALUES ('" + this.getId() + "', '" + phone + "')";

		Integer affected_rows = db.changeDBData(query);

		// Если добавление прошло успешно...
		if (affected_rows > 0) {

			// Добавляем запись о номере в общий список.
			this.phones.put(db.getLastInsertId().toString(), phone);

			return true;
		} else {
			return false;
		}

	}

	public boolean deletePhone(String phoneId) {
		DBWorker db = DBWorker.getInstance();
	
		if ((phoneId != null) && (!phoneId.equals("null"))) {
			Integer affected_rows = db.changeDBData("DELETE FROM `phone` WHERE `id`=" + phoneId);

			// Если удаление прошло успешно...
			if (affected_rows > 0) {

				this.phones.remove(phoneId);
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}


	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", middlename=" + middlename
				+ ", phones=" + phones + ""+ " HashCode= " + hashCode() +"]";
	}

	// ++++++++++++++++++++++++++++++++++++++
	// Геттеры и сеттеры
	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getMiddlename() {
		if ((this.middlename != null) && (!this.middlename.equals("null"))) {
			return this.middlename;
		} else {
			return "";
		}
	}

	public HashMap<String, String> getPhones() {
		return this.phones;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
//-----------------------------------------
	public boolean updatePhone(String phoneId, String newNumber) {
		
		DBWorker db = DBWorker.getInstance();
		Integer id_filtered = Integer.parseInt(this.getId());
		String query = "";

			query = "UPDATE `phone` SET `number` = '" + newNumber + "' WHERE `id` = " + phoneId;
		Integer affected_rows = db.changeDBData(query);

		// Если обновление прошло успешно...
		if (affected_rows > 0) {
			// Обновляем запись в общем списке.
			this.phones.put(phoneId, newNumber);
			return true;
		} else {
			return false;
		}
	}

	public void setPhones(HashMap<String, String> phones) {
		this.phones = phones;
	}
	// Геттеры и сеттеры
	// --------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((middlename == null) ? 0 : middlename.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phones == null) ? 0 : phones.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}
}
