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
        name = "quoteApi",
        version = "v1",
        resource = "quote",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hostel.iiitd.com",
                ownerName = "backend.hostel.iiitd.com",
                packagePath = ""
        )
)
public class QuoteEndpoint {

    private static final Logger logger = Logger.getLogger(QuoteEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Quote.class);
    }

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(QuoteEndpoint.class.getName());

    public QuoteEndpoint() {
    }

    /**
     * Return a collection of quotes
     *
     * @param count The number of quotes
     * @return a list of Quotes
     */
    @ApiMethod(name = "listQuote")
    public CollectionResponse<Quote> listQuote(@Nullable @Named("cursor") String cursorString,
                                               @Nullable @Named("count") Integer count)
    {
        //Quote record = findRecordWho(who);
       Query<Quote> query = ofy().load().type(Quote.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Quote> records = new ArrayList<Quote>();
        QueryResultIterator<Quote> iterator = query.iterator();
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
        return CollectionResponse.<Quote>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>Quote</code> object.
     *
     * @param quote The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertQuote")
    public Quote insertQuote(Quote quote) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (quote.getId() != null) {
            if (findRecord(quote.getId()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(quote).now();
        return quote;
    }

    /**
     * This updates an existing <code>Quote</code> object.
     *
     * @param quote The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateQuote")
    public Quote updateQuote(Quote quote) throws NotFoundException {
        if (findRecord(quote.getId()) == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().save().entity(quote).now();
        return quote;
    }

    /**
     * This deletes an existing <code>Quote</code> object.
     *
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeQuote")
    public void removeQuote(@Named("id") Long id) throws NotFoundException {
        Quote record = findRecord(id);
        if (record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>Quote</code> record
    private Quote findRecord(Long id)
    {
        return ofy().load().type(Quote.class).id(id).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }

    private List<Quote> findRecordWho(String who)
    {
        return ofy().load().type(Quote.class).filter("who", who).list();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }

    @ApiMethod(name = "searchQuoteUsingWho")
    public CollectionResponse<Quote> searchQuoteUsingWho(@Nullable @Named("cursor") String cursorString,
                                                         @Nullable @Named("count") Integer count,@Named("who") String who)
    {
        Query<Quote> query = ofy().load().type(Quote.class).filter("who", who);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Quote> records = new ArrayList<Quote>();
        QueryResultIterator<Quote> iterator = query.iterator();
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
        return CollectionResponse.<Quote>builder().setItems(records).setNextPageToken(cursorString).build();
    }
}
