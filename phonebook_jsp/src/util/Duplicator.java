package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Duplicator {

	public static <T> T duplicate(T obj) {
		T clone = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream ous = new ObjectOutputStream(baos);
			// сохраняем состояние объекта в поток

			ous.writeObject(obj);
			ous.close();

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			// создаём клон и инициализиуем
			clone = (T) ois.readObject();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Сlonning error");
			e.printStackTrace();
		}
		

		return clone;
	
	}
}
