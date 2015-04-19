package com.iiitd.hostel.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "studentRegisterApi",
        version = "v1",
        resource = "studentRegister",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hostel.iiitd.com",
                ownerName = "backend.hostel.iiitd.com",
                packagePath = ""
        )
)
public class StudentRegisterEndpoint
{

    private static final Logger logger = Logger.getLogger(StudentRegisterEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static
    {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(StudentRegister.class);
    }

    /**
     * Returns the {@link StudentRegister} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code StudentRegister} with the provided ID.
     */
    @ApiMethod(name = "get",path = "studentRegister/{id}",httpMethod = ApiMethod.HttpMethod.GET)
    public StudentRegister get(@Named("id") Long id) throws NotFoundException
    {
        logger.info("Getting StudentRegister with ID: " + id);
        StudentRegister studentRegister = ofy().load().type(StudentRegister.class).id(id).now();
        if (studentRegister == null) {
            throw new NotFoundException("Could not find StudentRegister with ID: " + id);
        }
        return studentRegister;
    }

    /**
     * Inserts a new {@code StudentRegister}.
     */
    @ApiMethod(name = "insert",path = "studentRegister",httpMethod = ApiMethod.HttpMethod.POST)
    public StudentRegister insert(StudentRegister studentRegister) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that studentRegister.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(studentRegister).now();
        logger.info("Created StudentRegister with ID: " + studentRegister.getId());

        return ofy().load().entity(studentRegister).now();
    }

    /**
     * Updates an existing {@code StudentRegister}.
     *
     * @param id              the ID of the entity to be updated
     * @param studentRegister the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code StudentRegister}
     */
    @ApiMethod(name = "update",path = "studentRegister/{id}",httpMethod = ApiMethod.HttpMethod.PUT)
    public StudentRegister update(@Named("id") Long id, StudentRegister studentRegister) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(studentRegister).now();
        logger.info("Updated StudentRegister: " + studentRegister);
        return ofy().load().entity(studentRegister).now();
    }

    /**
     * Deletes the specified {@code StudentRegister}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code StudentRegister}
     */
    @ApiMethod(name = "remove",path = "studentRegister/{id}",httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(StudentRegister.class).id(id).now();
        logger.info("Deleted StudentRegister with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(name = "list",path = "studentRegister",httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<StudentRegister> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<StudentRegister> query = ofy().load().type(StudentRegister.class).limit(limit);
        if (cursor != null)
        {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<StudentRegister> queryIterator = query.iterator();
        List<StudentRegister> studentRegisterList = new ArrayList<StudentRegister>(limit);
        while (queryIterator.hasNext())
        {
            studentRegisterList.add(queryIterator.next());
        }
        return CollectionResponse.<StudentRegister>builder().setItems(studentRegisterList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try
        {
            ofy().load().type(StudentRegister.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find StudentRegister with ID: " + id);
        }
    }
}