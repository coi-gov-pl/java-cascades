package pl.gov.coi.cascades.server.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.wavesoftware.eid.exceptions.Eid;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Optional;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * Service for database class type name.
 */
public class DatabaseTypeClassNameServiceImpl implements DatabaseTypeClassNameService {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseTypeClassNameService.class);

    /**
     * Method gives DTO of database type for given name of class type.
     *
     * @param typeClassName Given name of class type.
     */
    @Override
    public DatabaseTypeDTO getDatabaseType(String typeClassName) {
        // TODO: This is simple local implementation that is about to change
        Optional<Class<?>> clsOptional = findClassByFQCN(typeClassName);
        if (!clsOptional.isPresent()) {
            return newErrorDTO("Given class: %s is not available.", typeClassName);
        }
        Class<?> cls = clsOptional.get();
        if (DatabaseType.class.isAssignableFrom(cls)) {
            @SuppressWarnings("unchecked")
            Class<DatabaseType> databaseTypeClass = (Class<DatabaseType>) cls;
            if (isPossibleToInstantiate(databaseTypeClass)) {
                DatabaseType databaseType = instantiate(databaseTypeClass);
                return new DatabaseTypeDTO(databaseType);
            } else {
                return newErrorDTO(
                    "Given class: %s can not be instantiated. It must be public with public no arguments constructor.",
                    typeClassName
                );
            }
        } else {
            return newErrorDTO(
                "Given class: %s is not subclass of %s",
                typeClassName,
                DatabaseType.class.getName()
            );
        }
    }

    private DatabaseTypeDTO newErrorDTO(String messageFormat, Object... arguments) {
        return new DatabaseTypeDTO(
            new ErrorImpl(
                String.format(messageFormat, arguments)
            )
        );
    }

    private Optional<Class<?>> findClassByFQCN(String typeFQCN) {
        try {
            return Optional.of(Class.forName(typeFQCN));
        } catch (ClassNotFoundException e) {
            String msg = "Can't find given fully-qualified class name.";
            LOG.error(new Eid("20170308:122649").makeLogMessage(msg), e);
            return Optional.empty();
        }
    }

    private DatabaseType instantiate(Class<DatabaseType> databaseTypeClass) {
        return tryToExecute(() -> instantinateUnsafly(databaseTypeClass), "20170308:161616");
    }

    private DatabaseType instantinateUnsafly(Class<DatabaseType> databaseTypeClass) throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException, InstantiationException {

        return databaseTypeClass.getConstructor().newInstance();
    }

    private boolean hasParameterlessPublicConstructor(Class<?> cls) {
        for (Constructor<?> constructor : cls.getConstructors()) {
            if (constructor.getParameterCount() == 0
                && Modifier.isPublic(constructor.getModifiers())) {
                return true;
            }
        }
        return false;
    }

    private boolean isPossibleToInstantiate(Class<DatabaseType> databaseTypeClass) {
        return Modifier.isPublic(databaseTypeClass.getModifiers())
            && hasParameterlessPublicConstructor(databaseTypeClass);
    }

}
