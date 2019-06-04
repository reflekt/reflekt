package se.jensim.reflekt.usage;

import kotlin.Unit;
import org.junit.Test;
import se.jensim.reflekt.RefleKt;
import se.jensim.reflekt.RefleKtConf;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.Assert.assertNotEquals;
import static se.jensim.RefleKt.getRefleKt;
import static se.jensim.RefleKt.refleKt;

public class JavaRefleKtUsageTest {

    @Test
    public void useRefleKtInJava() {
        // given
        RefleKt refleKt = getRefleKt();
        refleKt = refleKt();
        refleKt = refleKt(conf -> {
                    conf.setPackageFilter("se.jensim");
                    return Unit.INSTANCE;
                }
        );
        RefleKtConf conf = new RefleKtConf();
        conf.setPackageFilter("se.jensim");
        refleKt = refleKt(conf);

        // when
        Set<String> allTypes = refleKt.getAllTypes();

        // then
        assertNotEquals(allTypes, emptySet());
    }
}
