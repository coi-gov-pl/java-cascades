package pl.gov.coi.cascades.supplier.string;

import com.google.common.base.Supplier;
import pl.wavesoftware.eid.utils.EidPreconditions.UnsafeSupplier;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
public final class InputStreamString implements Supplier<String> {
    private static final int BYTE_BUFFER_SIZE = 1024;
    private static final int NON_EXISTING_BYTE = -1;
    private static final int ZERO_OFFSET = 0;

    private final InputStream inputStream;

    /**
     * A constructor with input stream
     * @param inputStream a input stream
     */
    public InputStreamString(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String get() {
        return tryToExecute(new UnsafeSupplier<String>() {
            @Override @Nonnull
            public String get() throws IOException {
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[BYTE_BUFFER_SIZE];
                int length;
                while ((length = inputStream.read(buffer)) != NON_EXISTING_BYTE) {
                    result.write(buffer, ZERO_OFFSET, length);
                }
                return result.toString(StandardCharsets.UTF_8.name());
            }
        }, "20170330:133241");

    }
}
