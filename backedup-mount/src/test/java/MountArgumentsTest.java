import drive.MountArguments;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MountArgumentsTest {
	@Test
	void mountDriveForUserSeenisftp() {
		MountArguments mountArguments = new MountArguments("D:\\Programming\\Win-SSHFS\\Sshfs\\Sshfs\\bin\\Release\\WinSshFS.exe",
				"18.194.219.60", "2000", "seenisftp", "files", "seenisftp", "password");
		assertAll(() -> {
			assertEquals("D:\\Programming\\Win-SSHFS\\Sshfs\\Sshfs\\bin\\Release\\WinSshFS.exe", mountArguments.toCommandWithArguments().get(0));
			assertEquals("files", mountArguments.toCommandWithArguments().get(1));
			assertEquals("2000", mountArguments.toCommandWithArguments().get(2));
			assertEquals("seenisftp", mountArguments.toCommandWithArguments().get(3));
			assertEquals("18.194.219.60", mountArguments.toCommandWithArguments().get(4));
			assertEquals("seenisftp", mountArguments.toCommandWithArguments().get(5));
			assertEquals("password", mountArguments.toCommandWithArguments().get(6));
		});
	}
}
