/*
 * E: Ken@kenreid.co.uk
 */
package system;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;

/**
 * The Class AccessWin32.
 */
public class AccessWin32 {

	/**
	 * The Class Kernel32.
	 */
	static class Kernel32 {

		/** The process query information. */
		public static int PROCESS_QUERY_INFORMATION = 0x0400;

		/** The process vm read. */
		public static int PROCESS_VM_READ = 0x0010;
		static {
			Native.register("kernel32");
		}

		/**
		 * Gets the last error.
		 *
		 * @return the int
		 */
		public static native int GetLastError();

		/**
		 * Open process.
		 *
		 * @param dwDesiredAccess
		 *            the dw desired access
		 * @param bInheritHandle
		 *            the b inherit handle
		 * @param pointer
		 *            the pointer
		 * @return the pointer
		 */
		public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
	}

	/**
	 * The Class Psapi.
	 */
	static class Psapi {
		static {
			Native.register("psapi");
		}

		/**
		 * Gets the module base name W.
		 *
		 * @param hProcess
		 *            the h process
		 * @param hmodule
		 *            the hmodule
		 * @param lpBaseName
		 *            the lp base name
		 * @param size
		 *            the size
		 * @return the int
		 */
		public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
	}

	/**
	 * The Class User32DLL.
	 */
	static class User32DLL {
		static {
			Native.register("user32");
		}

		/**
		 * Gets the foreground window.
		 *
		 * @return the hwnd
		 */
		public static native HWND GetForegroundWindow();

		/**
		 * Gets the window text W.
		 *
		 * @param hWnd
		 *            the h wnd
		 * @param lpString
		 *            the lp string
		 * @param nMaxCount
		 *            the n max count
		 * @return the int
		 */
		public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);

		/**
		 * Gets the window thread process id.
		 *
		 * @param hWnd
		 *            the h wnd
		 * @param pref
		 *            the pref
		 * @return the int
		 */
		public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
	}

	/** The Constant MAX_TITLE_LENGTH. */
	private static final int MAX_TITLE_LENGTH = 1024;

	/**
	 * Gets the active window title.
	 *
	 * @return the active window title
	 */
	public String getActiveWindowTitle() {
		final char[] buffer = new char[AccessWin32.MAX_TITLE_LENGTH * 2];
		final HWND hwnd = User32.INSTANCE.GetForegroundWindow();
		User32.INSTANCE.GetWindowText(hwnd, buffer, AccessWin32.MAX_TITLE_LENGTH);
		return Native.toString(buffer);
	}
}