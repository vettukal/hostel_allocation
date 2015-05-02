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
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        name = "allocationStatusApi",
        version = "v1",
        resource = "allocationStatus",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hostel.iiitd.com",
                ownerName = "backend.hostel.iiitd.com",
                packagePath = ""
        )
)
public class AllocationStatusEndpoint {
    //private static final Logger logger = Logger.getLogger(AllocationStatus.class.getName());

    private static final Logger logger = Logger.getLogger(AllocationStatusEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(AllocationStatus.class);
    }

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(AllocationStatusEndpoint.class.getName());

    public AllocationStatusEndpoint() {
    }

    /**
     * Return a collection of allocationStatuss
     *
     * @param count The number of allocationStatuss
     * @return a list of AllocationStatuss
     */
    @ApiMethod(name = "listAllocationStatus")
    public CollectionResponse<AllocationStatus> listAllocationStatus(@Nullable @Named("cursor") String cursorString,
                                                                     @Nullable @Named("count") Integer count)
    {
        //AllocationStatus record = findRecordWho(who);
        Query<AllocationStatus> query = ofy().load().type(AllocationStatus.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<AllocationStatus> records = new ArrayList<AllocationStatus>();
        QueryResultIterator<AllocationStatus> iterator = query.iterator();
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
        return CollectionResponse.<AllocationStatus>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>AllocationStatus</code> object.
     *
     * @param allocationStatus The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertAllocationStatus")
    public AllocationStatus insertAllocationStatus(AllocationStatus allocationStatus) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (allocationStatus.getId() != null) {
            if (findRecord(allocationStatus.getId()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(allocationStatus).now();
        return allocationStatus;
    }

    /**
     * This updates an existing <code>AllocationStatus</code> object.
     *
     * @param allocationStatus The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateAllocationStatus")
    public AllocationStatus updateAllocationStatus(AllocationStatus allocationStatus) throws NotFoundException {
        if (findRecord(allocationStatus.getId()) == null) {
            throw new NotFoundException("AllocationStatus Record does not exist");
        }
        ofy().save().entity(allocationStatus).now();
        return allocationStatus;
    }

    /**
     * This deletes an existing <code>AllocationStatus</code> object.
     *
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeAllocationStatus")
    public void removeAllocationStatus(@Named("id") Long id) throws NotFoundException {
        AllocationStatus record = findRecord(id);
        if (record == null) {
            throw new NotFoundException("AllocationStatus Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>AllocationStatus</code> record
    private AllocationStatus findRecord(Long id)
    {
        return ofy().load().type(AllocationStatus.class).id(id).now();
        //or return ofy().load().type(AllocationStatus.class).filter("id",id).first.now();
    }

    private List<AllocationStatus> findRecordWho(String who)
    {
        return ofy().load().type(AllocationStatus.class).filter("who", who).list();
        //or return ofy().load().type(AllocationStatus.class).filter("id",id).first.now();
    }

    @ApiMethod(name = "searchAllocationStatusUsingWho")
    public CollectionResponse<AllocationStatus> searchAllocationStatusUsingWho(@Nullable @Named("cursor") String cursorString,
                                                                               @Nullable @Named("count") Integer count,@Named("who") String who)
    {
        Query<AllocationStatus> query = ofy().load().type(AllocationStatus.class).filter("who", who);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<AllocationStatus> records = new ArrayList<AllocationStatus>();
        QueryResultIterator<AllocationStatus> iterator = query.iterator();
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
        return CollectionResponse.<AllocationStatus>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    @ApiMethod(name = "allocateHostel" , path = "allocateHostel")
    public CollectionResponse<AllocationStatus> allocateHostel(){

        // copying code for student list according to distance
        String cursorString = null;
        Integer count = null;
        Boolean testtrue = true;
        Query<Student> query = ofy().load().type(Student.class).filter("isApplied",testtrue);
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
        CollectionResponse<Student> students =  CollectionResponse.<Student>builder().setItems(records).setNextPageToken(cursorString).build();
        List<Student> list = new ArrayList(students.getItems());
        Collections.sort(list);

        //Collections.sort(students);






        return null;
    }

    private List<Student> listStudents(){
        String cursorString = null;
        Integer count = null;
        Boolean testtrue = true;
        Query<Student> query = ofy().load().type(Student.class).filter("isApplied",testtrue);
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
        CollectionResponse<Student> students =  CollectionResponse.<Student>builder().setItems(records).setNextPageToken(cursorString).build();
        return new ArrayList<>(students.getItems());
    }

    //@ApiMethod(name = "testHostelKamra" , path = "testHostelKamra")
    private List<Kamra> testHostelKamra(){

//        Integer count = null;
//        String cursorString = null;
//        //Room record = findRecordWho(who);
//        Query<Room> query = ofy().load().type(Room.class);
//        if (count != null) query.limit(count);
//        if (cursorString != null && cursorString != "") {
//            query = query.startAt(Cursor.fromWebSafeString(cursorString));
//        }
//
//        List<Room> records = new ArrayList<Room>();
//        QueryResultIterator<Room> iterator = query.iterator();
//        int num = 0;
//        while (iterator.hasNext()) {
//            records.add(iterator.next());
//            if (count != null) {
//                num++;
//                if (num == count) break;
//            }
//        }
//
//        //Find the next cursor
//        if (cursorString != null && cursorString != "") {
//            Cursor cursor = iterator.getCursor();
//            if (cursor != null) {
//                cursorString = cursor.toWebSafeString();
//            }
//        }
//        CollectionResponse<Room> rooms = CollectionResponse.<Room>builder().setItems(records).setNextPageToken(cursorString).build();
//        Collection<Room> coll = rooms.getItems();
//        List<Room> roomList = new ArrayList<Room>(coll);
//        // going to remove and then insert the rooms in the database.
//
//        List<Kamra> kamraList =  new ArrayList<>();
//        for(Room r:roomList){
//            Kamra k = new Kamra();
//            k.setFloor(r.getFloor());
//            k.setHostelType(r.getHostelType());
//            k.setRoomNumber(r.getRoomNumber());
//            k.setRoomType(r.getRoomType());
//            k.setWing(r.getWing());
//            k.setStatus(false);
//
//            try{
//                kamraList.add(k);
//                ofy().save().entity(k).now();
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
//        return kamraList;
        return null;
    }

    @ApiMethod(name = "testHostel" , path = "testHostel")
    public void testHostel(){

        // copying code for student list according to distance
        String cursorString = null;
        Integer count = null;
        Boolean testtrue = true;
        Query<Student> query = ofy().load().type(Student.class).filter("isApplied",testtrue);
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
        CollectionResponse<Student> students =  CollectionResponse.<Student>builder().setItems(records).setNextPageToken(cursorString).build();
        List<Student> listStudents = new ArrayList(students.getItems());
        Collections.sort(listStudents);

        List<Kamra> kamraList = new ArrayList(listKamras().getItems());

        List<Kamra> boyKamra = new ArrayList<>();
        for(Kamra bk : kamraList){
            if(bk.getHostelType().equals("BH")){
                boyKamra.add(bk);
            }
        }

        List<Kamra> girlKamra = new ArrayList<>();
        for(Kamra gk : kamraList){
            if(gk.getHostelType().equals("GH")){
                girlKamra.add(gk);
            }
        }

        List<AllocationStatus> allocationStatusList = new ArrayList<>();
        for(Student student: listStudents){
            String gender = student.getGender();
            int boycount = 0;
            int girlcount = 0;
            // Update kamra to the server with new status.
            // Make allocationStatus object
            // Add Allocation status with kamra details to the list.
            // Insert AllocationStatus to the server.

            if(gender.equalsIgnoreCase("Male")){
                Kamra bk = boyKamra.get(boycount++);
                bk.setStatus(true);
                // Update kamra to the server with new status.
                updateKamra(bk);

                // Make allocationStatus object
                AllocationStatus aso = new AllocationStatus();
                aso.setRoomNumber(bk.getRoomNumber());
                aso.setWing(bk.getWing());
                aso.setFloor(bk.getFloor());
                aso.setEmailId(student.getEmailId());

                // Add Allocation status with kamra details to the list.
                allocationStatusList.add(aso);

                // Insert AllocationStatus to the server.
                ofy().save().entity(aso).now();
            }
            else {
                Kamra gk = girlKamra.get(girlcount++);
                gk.setStatus(true);
                // Update kamra to the server with new status.
                updateKamra(gk);

                // Make allocationStatus object
                AllocationStatus aso = new AllocationStatus();
                aso.setRoomNumber(gk.getRoomNumber());
                aso.setWing(gk.getWing());
                aso.setFloor(gk.getFloor());
                aso.setEmailId(student.getEmailId());

                // Add Allocation status with kamra details to the list.
                allocationStatusList.add(aso);

                // Insert AllocationStatus to the server.
                ofy().save().entity(aso).now();
            }
        }


    }

    private Kamra updateKamra(Kamra kamra)  {
        if (findRecord(kamra.getId()) == null) {
            //throw new NotFoundException("Kamra Record does not exist");
        }
        ofy().save().entity(kamra).now();
        return kamra;
    }

    private CollectionResponse<Kamra> listKamras(){
        Integer count = null;
        String cursorString = null;
        //Kamra record = findRecordWho(who);
        Boolean falsebool = false;
        Query<Kamra> query = ofy().load().type(Kamra.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Kamra> records = new ArrayList<Kamra>();
        QueryResultIterator<Kamra> iterator = query.iterator();
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
        return CollectionResponse.<Kamra>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    @ApiMethod(name = "testHostelStudents" , path = "testHostelStudents")
    public List<Student> testHostelKamraStudents(){
        // copying code for student list according to distance
        String cursorString = null;
        Integer count = null;
        Boolean testtrue = true;
        Query<Student> query = ofy().load().type(Student.class).filter("isApplied",testtrue);
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
        CollectionResponse<Student> students =  CollectionResponse.<Student>builder().setItems(records).setNextPageToken(cursorString).build();
        List<Student> listStudents = new ArrayList(students.getItems());
        Collections.sort(listStudents);

        return listStudents;
    }

    @ApiMethod(name = "testHostelKK2" , path = "testHostelKK2")
    public List<Kamra> testHostelKK2(){
        // copying code for student list according to distance

        String cursorString = null;
        Integer count = null;
        Boolean testtrue = true;
        Query<Student> query = ofy().load().type(Student.class).filter("isApplied", testtrue);
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
        CollectionResponse<Student> students =  CollectionResponse.<Student>builder().setItems(records).setNextPageToken(cursorString).build();
        List<Student> listStudents = new ArrayList(students.getItems());
        Collections.sort(listStudents);

        List<Kamra> kamraList = new ArrayList(listKamras().getItems());

        List<Kamra> boyKamra = new ArrayList<>();
        //logger.info("size of the kamra"+kamraList.size());
        for(Kamra bk : kamraList){
            if(bk.getHostelType()!=null){
                if(bk.getHostelType().equals("BH")){
                    boyKamra.add(bk);
                }
            }

        }

        List<Kamra> girlKamra = new ArrayList<>();
        for(Kamra gk : kamraList){
            if(gk.getHostelType()!=null){
                if(gk.getHostelType().equals("GH")){
                    girlKamra.add(gk);
                }
            }

        }

        List<AllocationStatus> allocationStatusList = new ArrayList<>();
        return kamraList;
    }

    @ApiMethod(name = "testHostelDelete" , path = "testHostelDelete")
    public List<Kamra> testHostelDelete(){
        List<Kamra> kamraList = new ArrayList(listKamras().getItems());
        for(Kamra k:kamraList){
            ofy().delete().entity(k).now();
        }
        return kamraList;
    }

    @ApiMethod(name = "AllocationStatusDelete" , path = "HostelAllocationStatusDelete")
    public void AllocationStatusDelete (){
        Integer count = null;
        String cursorString = null;
        Query<AllocationStatus> query = ofy().load().type(AllocationStatus.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<AllocationStatus> records = new ArrayList<AllocationStatus>();
        QueryResultIterator<AllocationStatus> iterator = query.iterator();
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
        CollectionResponse<AllocationStatus> alcollection =  CollectionResponse.<AllocationStatus>builder().setItems(records).setNextPageToken(cursorString).build();
        List<AllocationStatus> alist = new ArrayList<>(alcollection.getItems());

        for(AllocationStatus as: alist){
            ofy().delete().entity(as).now();
        }
    }

    @ApiMethod(name = "KamraFalse" , path = "KamraFalse")
    public List<Kamra> KamraFalse(){
        List<Kamra> kamraList = new ArrayList<>(listKamras().getItems());
        List<Kamra> result = new ArrayList<>();
        for(Kamra k: kamraList){
            k.setStatus(false);
            ofy().save().entity(k).now();
            result.add(k);
        }
        return result;
    }

    @ApiMethod(name = "deleteStudent" , path = "deleteStudent")
    public void deleteStudent(){
        List<Student> students = listStudents();
        for(Student stud: students){
            ofy().delete().entity(stud).now();
        }
    }

    private void delete(Long id){
//        Kamra record = findRecord(id);
//        if (record == null) {
//            throw new NotFoundException("Kamra Record does not exist");
//        }
//        ofy().delete().entity(record).now();
    }
}
