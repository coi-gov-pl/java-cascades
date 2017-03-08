package pl.gov.coi.cascades.server.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.wavesoftware.eid.exceptions.Eid;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Optional;

/**
 * Service for database class type name.
 */
public class DatabaseTypeClassNameService {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseTypeClassNameService.class);

    /**
     * Method gives DTO of database type for given name of class type.
     *
     * @param typeClassName Given name of class type.
     */
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
        if (hasParameterlessPublicConstructor(databaseTypeClass)
            && Modifier.isPublic(databaseTypeClass.getModifiers())) {
            try {
                return databaseTypeClass.getConstructor().newInstance();
            } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
                LOG.error(new Eid("20170308:122021")
                        .makeLogMessage("Given database type of class is not public or doesn't have public constructor without parameters."), e
                );
            }
        }

        return null;
    }

    private boolean hasParameterlessPublicConstructor(Class<?> cls) {
        for (Constructor<?> constructor : cls.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isPossibleToInstantiate(Class<DatabaseType> databaseTypeClass) {
        try {
            databaseTypeClass.getConstructor().newInstance();
        } catch (InstantiationException
            | IllegalAccessException
            | InvocationTargetException
            | NoSuchMethodException e) {
            LOG.error(new Eid("20170308:114024")
                    .makeLogMessage("Can't instantiate given database type of class."), e
            );
            return false;
        }
        return true;
    }

}
