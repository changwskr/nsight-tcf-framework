package nhnis.infra.in.a.dto;

import java.util.*;
import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class ifina2400S0DTOout extends DataObject {
    private static final long serialVersionUID = 1L;
    private List<Map<String, Object>> systems = new ArrayList<>();
    private List<Map<String, Object>> domains = new ArrayList<>();
    private List<Map<String, Object>> units = new ArrayList<>();
    private List<Map<String, Object>> details = new ArrayList<>();
    private List<Map<String, Object>> apps = new ArrayList<>();
    private List<Map<String, Object>> groups = new ArrayList<>();
    private List<Map<String, Object>> servers = new ArrayList<>();
    private List<Map<String, Object>> databases = new ArrayList<>();
    private List<Map<String, Object>> middlewares = new ArrayList<>();
    private List<Map<String, Object>> maps = new ArrayList<>();
    private List<Map<String, Object>> bizAppMaps = new ArrayList<>();
    private List<Map<String, Object>> previewLines = new ArrayList<>();
    private List<Map<String, Object>> overviewUnits = new ArrayList<>();
    private List<Map<String, Object>> mapSummary = new ArrayList<>();
    private List<Map<String, Object>> resultSummary = new ArrayList<>();
    private Map<String, Object> selectedApp = new LinkedHashMap<>();
    private Map<String, Object> selectedBiz = new LinkedHashMap<>();
    private Map<String, Object> appRuntime = new LinkedHashMap<>();
    private Map<String, Object> bizTree = new LinkedHashMap<>();
    private Map<String, Object> session = new LinkedHashMap<>();
    private String RSLT_CD = "0000", RSLT_MSG = "OK";

    public List<Map<String, Object>> getSystems(){return systems;} public void setSystems(List<Map<String, Object>> v){systems=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getDomains(){return domains;} public void setDomains(List<Map<String, Object>> v){domains=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getUnits(){return units;} public void setUnits(List<Map<String, Object>> v){units=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getDetails(){return details;} public void setDetails(List<Map<String, Object>> v){details=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getApps(){return apps;} public void setApps(List<Map<String, Object>> v){apps=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getGroups(){return groups;} public void setGroups(List<Map<String, Object>> v){groups=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getServers(){return servers;} public void setServers(List<Map<String, Object>> v){servers=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getDatabases(){return databases;} public void setDatabases(List<Map<String, Object>> v){databases=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getMiddlewares(){return middlewares;} public void setMiddlewares(List<Map<String, Object>> v){middlewares=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getMaps(){return maps;} public void setMaps(List<Map<String, Object>> v){maps=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getBizAppMaps(){return bizAppMaps;} public void setBizAppMaps(List<Map<String, Object>> v){bizAppMaps=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getPreviewLines(){return previewLines;} public void setPreviewLines(List<Map<String, Object>> v){previewLines=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getOverviewUnits(){return overviewUnits;} public void setOverviewUnits(List<Map<String, Object>> v){overviewUnits=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getMapSummary(){return mapSummary;} public void setMapSummary(List<Map<String, Object>> v){mapSummary=v!=null?v:new ArrayList<>();}
    public List<Map<String, Object>> getResultSummary(){return resultSummary;} public void setResultSummary(List<Map<String, Object>> v){resultSummary=v!=null?v:new ArrayList<>();}
    public Map<String, Object> getSelectedApp(){return selectedApp;} public void setSelectedApp(Map<String, Object> v){selectedApp=v!=null?v:new LinkedHashMap<>();}
    public Map<String, Object> getSelectedBiz(){return selectedBiz;} public void setSelectedBiz(Map<String, Object> v){selectedBiz=v!=null?v:new LinkedHashMap<>();}
    public Map<String, Object> getAppRuntime(){return appRuntime;} public void setAppRuntime(Map<String, Object> v){appRuntime=v!=null?v:new LinkedHashMap<>();}
    public Map<String, Object> getBizTree(){return bizTree;} public void setBizTree(Map<String, Object> v){bizTree=v!=null?v:new LinkedHashMap<>();}
    public Map<String, Object> getSession(){return session;} public void setSession(Map<String, Object> v){session=v!=null?v:new LinkedHashMap<>();}
    public String getRSLT_CD(){return RSLT_CD;} public void setRSLT_CD(String v){RSLT_CD=v;}
    public String getRSLT_MSG(){return RSLT_MSG;} public void setRSLT_MSG(String v){RSLT_MSG=v;}

    @Override public Object clone(){ ifina2400S0DTOout c=new ifina2400S0DTOout(); c.clone(this); return c; }
    public void clone(DataObject src){ if(this==src)return; ifina2400S0DTOout in=(ifina2400S0DTOout)src;
      systems=copyList(in.systems); domains=copyList(in.domains); units=copyList(in.units); details=copyList(in.details);
      apps=copyList(in.apps); groups=copyList(in.groups); servers=copyList(in.servers); databases=copyList(in.databases);
      middlewares=copyList(in.middlewares); maps=copyList(in.maps); bizAppMaps=copyList(in.bizAppMaps);
      previewLines=copyList(in.previewLines); overviewUnits=copyList(in.overviewUnits);
      mapSummary=copyList(in.mapSummary); resultSummary=copyList(in.resultSummary);
      selectedApp=copyMap(in.selectedApp); selectedBiz=copyMap(in.selectedBiz); appRuntime=copyMap(in.appRuntime);
      bizTree=copyMap(in.bizTree); session=copyMap(in.session); RSLT_CD=in.RSLT_CD; RSLT_MSG=in.RSLT_MSG; }
    private static List<Map<String, Object>> copyList(List<Map<String, Object>> src){
      if(src==null) return new ArrayList<>();
      List<Map<String, Object>> out=new ArrayList<>();
      for(Map<String, Object> m: src) out.add(m==null?null:new LinkedHashMap<>(m));
      return out;
    }
    private static Map<String, Object> copyMap(Map<String, Object> src){ return src==null?new LinkedHashMap<>():new LinkedHashMap<>(src); }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static { fieldPropertyMap.put("RSLT_CD", FieldProperty.builder().setPhysicalName("RSLT_CD").setLogicalName("RSLT_CD").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build()); }
    public Map<String, FieldProperty> getFieldPropertyMap(){ return Collections.unmodifiableMap(fieldPropertyMap); }
}
