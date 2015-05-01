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
        name = "studentApi",
        version = "v2",
        resource = "student",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hostel.iiitd.com",
                ownerName = "backend.hostel.iiitd.com",
                packagePath = ""
        )
)
public class StudentEndpoint {

    private static final Logger logger = Logger.getLogger(StudentEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Student.class);
    }

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(StudentEndpoint.class.getName());

    public StudentEndpoint() {
    }

    /**
     * Return a collection of students
     *
     * @param count The number of students
     * @return a list of Students
     */
    @ApiMethod(name = "listStudent")
    public CollectionResponse<Student> listStudent(@Nullable @Named("cursor") String cursorString,
                                                   @Nullable @Named("count") Integer count) {

        //Query<Student> query = ofy().load().type(Student.class);
        Query<Student> query = ofy().load().type(Student.class).order("-distance");
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Student> records = new ArrayList<Student>();
        QueryResultIterator<Student> iterator = query.iterator();
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
        return CollectionResponse.<Student>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>Student</code> object.
     *
     * @param student The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertStudent")
    public Student insertStudent(Student student) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (student.getId() != null) {
            if (findRecord(student.getId()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(student).now();
        return student;
    }

    /**
     * This updates an existing <code>Student</code> object.
     *
     * @param student The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateStudent")
    public Student updateStudent(Student student) throws NotFoundException {
        if (findRecord(student.getId()) == null) {
            throw new NotFoundException("Student Record does not exist");
        }
        ofy().save().entity(student).now();
        return student;
    }

    /**
     * This deletes an existing <code>Student</code> object.
     *
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeStudent")
    public void removeStudent(@Named("id") Long id) throws NotFoundException {
        Student record = findRecord(id);
        if (record == null) {
            throw new NotFoundException("Student Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>Student</code> record
    private Student findRecord(Long id) {
        return ofy().load().type(Student.class).id(id).now();
        //or return ofy().load().type(Student.class).filter("id",id).first.now();
    }
}
