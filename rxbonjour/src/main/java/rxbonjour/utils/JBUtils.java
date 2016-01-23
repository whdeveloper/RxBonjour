package rxbonjour.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Build;

import java.io.IOException;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public final class JBUtils extends BonjourUtils<NsdManager> {

	private static JBUtils instance;

	/** NsdManager instance used for discovery, shared among subscribers */
	private NsdManager nsdManagerInstance;

	/** Synchronization lock on the NsdManager instance */
	private final Object nsdManagerLock = new Object();

	private JBUtils() {
		//no instance
	}

	public static JBUtils get() {
		if (instance == null) {
			instance = new JBUtils();
		}
		return instance;
	}

	@Override public NsdManager getManager(Context context) throws IOException {
		synchronized (nsdManagerLock) {
			if (nsdManagerInstance == null) {
				nsdManagerInstance = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
			}
			return nsdManagerInstance;
		}
	}
}
