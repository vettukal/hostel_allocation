package com.iiitd.hostel.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
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
        name = "allocateApi",
        version = "v1",
        resource = "allocate",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hostel.iiitd.com",
                ownerName = "backend.hostel.iiitd.com",
                packagePath = ""
        )
)

public class AllocateEndpoint {

    private static final Logger logger = Logger.getLogger(AllocateEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Allocate.class);
    }

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(AllocateEndpoint.class.getName());

    public AllocateEndpoint() {
    }

    /**
     * Return a collection of allocates
     *
     * @param count The number of allocates
     * @return a list of Allocates
     */
    @ApiMethod(name = "listAllocate")
    public CollectionResponse<Allocate> listAllocate(@Nullable @Named("cursor") String cursorString,
                                               @Nullable @Named("count") Integer count)
    {
        //Allocate record = findRecordWho(who);
        Query<Allocate> query = ofy().load().type(Allocate.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Allocate> records = new ArrayList<Allocate>();
        QueryResultIterator<Allocate> iterator = query.iterator();
        int num = 0;
        while (iterator.hasNext()) {
            records.add(iterator.next());
            if (count != null) {
                num++;
                if (num == count) break;
            }
        }

        //Find the next cursor
        if (cursorString != null && cursorString != "") {
            Cursor cursor = iterator.getCursor();
            if (cursor != null) {
                cursorString = cursor.toWebSafeString();
            }
        }
        return CollectionResponse.<Allocate>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>Allocate</code> object.
     *
     * @param allocate The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertAllocate")
    public Allocate insertAllocate(Allocate allocate) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (allocate.getId() != null) {
            if (findRecord(allocate.getId()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(allocate).now();
        return allocate;
    }

    /**
     * This updates an existing <code>Allocate</code> object.
     *
     * @param allocate The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateAllocate")
    public Allocate updateAllocate(Allocate allocate) throws NotFoundException {
        if (findRecord(allocate.getId()) == null) {
            throw new NotFoundException("Allocate Record does not exist");
        }
        ofy().save().entity(allocate).now();
        return allocate;
    }

    /**
     * This deletes an existing <code>Allocate</code> object.
     *
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeAllocate")
    public void removeAllocate(@Named("id") Long id) throws NotFoundException {
        Allocate record = findRecord(id);
        if (record == null) {
            throw new NotFoundException("Allocate Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>Allocate</code> record
    private Allocate findRecord(Long id)
    {
        return ofy().load().type(Allocate.class).id(id).now();
        //or return ofy().load().type(Allocate.class).filter("id",id).first.now();
    }

    private List<Allocate> findRecordWho(String who)
    {
        return ofy().load().type(Allocate.class).filter("who", who).list();
        //or return ofy().load().type(Allocate.class).filter("id",id).first.now();
    }

    @ApiMethod(name = "searchAllocateUsingWho")
    public CollectionResponse<Allocate> searchAllocateUsingWho(@Nullable @Named("cursor") String cursorString,
                                                         @Nullable @Named("count") Integer count,@Named("who") String who)
    {
        Query<Allocate> query = ofy().load().type(Allocate.class).filter("who", who);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Allocate> records = new ArrayList<Allocate>();
        QueryResultIterator<Allocate> iterator = query.iterator();
        int num = 0;
        while (iterator.hasNext()) {
            records.add(iterator.next());
            if (count != null) {
                num++;
                if (num == count) break;
            }
        }

        //Find the next cursor
        if (cursorString != null && cursorString != "") {
            Cursor cursor = iterator.getCursor();
            if (cursor != null) {
                cursorString = cursor.toWebSafeString();
            }
        }
        return CollectionResponse.<Allocate>builder().setItems(records).setNextPageToken(cursorString).build();
    }


}
