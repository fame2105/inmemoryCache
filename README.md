# inmemoryCache
Tavisca Assignment Project-
This Memory Cache Application is Developed to Achive Following Things -
 - Any get calls to the underlying data store should be cached in memory.
 - There is a limited size to the cache.
 - Most recently used objects should be available in cache and the ones which are not accessed very frequently should be discarded.
  - There should be provisions to flush the cache.
  - Any update to any record should update the cache and then the underlying store.(In case the updated object is present in the cache)

This Cache application is Developed based on LRU Cache which have HashMap and Doubly LinkedList, In Which HashMap will hold the keys and address of the Nodes of Doubly LinkedList And Doubly LinkedList will hold the values of keys.

We will remove element from bottom and add element on start of LinkedList and whenever any entry is accessed , it will be moved to top. so that recently used entries will be on Top and Least used will be on Bottom.

If count reaches to Capacity then we will remove the node from Bottom.

#### Getting Started
LRUCache takes cacheSize as the argument to provide a SINGLETON instance of this cache
1. Instance of this cache could be obtained using :
``` LRUCache cache = LRUCache.getInstance(cacheSize);```
2. To add/update any object into this cache:
```cache.put(String key, Object object)```  if the passed ID already exists then it will update the object instead of creating a new one

3. To  delete any item from cache:
``` cache.remove(String key)```
4. To retrieve any items from the cache.
```cache.get(String key)```
5. in order to flush the entire queue
``` cache.flush()```  this operation will empty the cache completely.

#### Usage Example
```sh
public class InmemoryLrucacheApplicationTests {
    private static final int cacheSize = 3;
    
    @InjectMocks
    private EmployeeService employeeService;
    private static LRUCache cache = LRUCache.getInstance(cacheSize);
    
 @Test(expected = KeyNotFoundException.class)
    public void saveDataIntoCache() throws KeyNotFoundException {
        List<Employee> employees = getEmployeeData();
        Employee e1 = employees.get(0);
        Employee e2 = employees.get(1);
        Employee e3 = employees.get(2);
        Employee e4 = employees.get(3);
        Employee e5 = employees.get(4);

        cache.put(String.valueOf(e1.getId()), e1);
        cache.put(String.valueOf(e2.getId()), e2);
        cache.put(String.valueOf(e3.getId()), e3);
        cache.put(String.valueOf(e4.getId()), e4);
        cache.put(String.valueOf(e5.getId()), e5);

        Employee cacheEmployee5 = (Employee) cache.get(String.valueOf(e5.getId()));
        Employee cacheEmployee4 = (Employee) cache.get(String.valueOf(e4.getId()));
        Employee cacheEmployee3 = (Employee) cache.get(String.valueOf(e3.getId()));

        assertEquals(e5.getName(), cacheEmployee5.getName());
        assertEquals(e4.getName(), cacheEmployee4.getName());
        assertEquals(e3.getName(), cacheEmployee3.getName());
        Employee cacheEmployee1 = (Employee) cache.get(String.valueOf(e1.getId()));
}

    @Test
    public void updateCacheData() throws KeyNotFoundException{
        Employee e1 = getEmployeeData().get(0);
        cache.put(String.valueOf(e1.getId()), e1);

        Employee cacheEmployee1 = (Employee) cache.get(String.valueOf(e1.getId()));
        assertEquals(e1.getName(), cacheEmployee1.getName());

        e1.setName("updatedName");
        employeeService.save(e1);

        cacheEmployee1 = (Employee) cache.get(String.valueOf(e1.getId()));
        assertEquals(e1.getName(), cacheEmployee1.getName());
    }

    @Test(expected = KeyNotFoundException.class)
    public void deleteDataFromCache() throws KeyNotFoundException{
        List<Employee> employees = getEmployeeData();
        Employee e1 = employees.get(0);
        Employee e2 = employees.get(1);
        Employee e3 = employees.get(2);

        cache.put(String.valueOf(e1.getId()), e1);
        cache.put(String.valueOf(e2.getId()), e2);
        cache.put(String.valueOf(e3.getId()), e3);

        assertEquals(3, cache.getTotalItemsInCache());

        cache.remove(String.valueOf(e1.getId()));

        assertEquals(2, cache.getTotalItemsInCache());

        // will throw keyNotFoundException when trying to access the deleted object from cache
        cache.get(String.valueOf(e1.getId()));
    }

    @Test()
    public void flushCache(){
        List<Employee> employees = getEmployeeData();
        Employee e1 = employees.get(0);
        Employee e2 = employees.get(1);
        Employee e3 = employees.get(2);
        Employee e4 = employees.get(3);
        Employee e5 = employees.get(4);

        cache.put(String.valueOf(e1.getId()), e1);
        cache.put(String.valueOf(e2.getId()), e2);
        cache.put(String.valueOf(e3.getId()), e3);
        cache.put(String.valueOf(e4.getId()), e4);
        cache.put(String.valueOf(e5.getId()), e5);

        assertEquals(3, cache.getTotalItemsInCache());
        cache.flush();
        assertEquals(0, cache.getTotalItemsInCache());
    }
}
```
