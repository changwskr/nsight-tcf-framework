package nhnis.infra.in.a.application.service;

import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.stereotype.Service;
import nhnis.infra.in.a.application.support.AuthGuard;
import nhnis.infra.in.a.application.support.ChangeLogWriter;
import nhnis.infra.in.a.dto.*;
import nhnis.infra.in.a.persistence.dao.ifina2400DAO;

@Service
public class ifina2400Service {
    private final ifina2400DAO dao;
    private final ChangeLogWriter changeLogWriter;
    private final AuthGuard authGuard;

    public ifina2400Service(ifina2400DAO dao, ChangeLogWriter changeLogWriter, AuthGuard authGuard) {
        this.dao = dao;
        this.changeLogWriter = changeLogWriter;
        this.authGuard = authGuard;
    }

    public ifina2400S0DTOout ifina2400S0(ifina2400S0DTOin input) throws Exception {
        String systemId = blank(input == null ? null : input.getSystemId(), "SYS-MKTG");
        String domainCd = trim(input == null ? null : input.getDomainCd());
        String bizCd = trim(input == null ? null : input.getBizCd());
        String detailCd = trim(input == null ? null : input.getDetailCd());
        String envCd = blank(input == null ? null : input.getEnvCd(), "PROD");
        String appId = trim(input == null ? null : input.getAppId());

        Map<String, Object> base = new HashMap<>();
        put(base, "systemId", systemId);
        put(base, "envCd", envCd);

        List<Map<String, Object>> systems = mapList(dao.ifina2400S0_systems(base), this::mapSystem);
        List<Map<String, Object>> domains = mapList(dao.ifina2400S0_domains(base), this::mapDomain);
        if (domainCd == null) {
            for (Map<String, Object> d : domains) {
                if ("MG".equals(d.get("domainCd"))) { domainCd = "MG"; break; }
            }
            if (domainCd == null && !domains.isEmpty()) domainCd = str(domains.get(0).get("domainCd"));
        }

        Map<String, Object> unitParam = new HashMap<>(base);
        put(unitParam, "domainCd", domainCd);
        List<Map<String, Object>> units = mapList(dao.ifina2400S0_units(unitParam), this::mapUnit);

        Map<String, Object> detailParam = new HashMap<>(base);
        put(detailParam, "domainCd", domainCd);
        List<Map<String, Object>> allDetails = mapList(dao.ifina2400S0_detailsBySystem(detailParam), this::mapDetailFull);

        Map<String, Map<String, Object>> countByBiz = new HashMap<>();
        Map<String, Object> countParam = new HashMap<>(base);
        put(countParam, "domainCd", domainCd);
        for (Map<String, Object> c : mapList(dao.ifina2400S0_bizAppMapCounts(countParam), this::mapCount)) {
            countByBiz.put(str(c.get("bizCd")), c);
        }

        if (bizCd == null) {
            for (Map<String, Object> u : units) {
                if ("CO".equals(u.get("bizCd"))) { bizCd = "CO"; break; }
            }
            if (bizCd == null) {
                for (Map<String, Object> u : units) {
                    if ("IC".equals(u.get("bizCd"))) { bizCd = "IC"; break; }
                }
            }
            if (bizCd == null) {
                for (Map<String, Object> u : units) {
                    if ("CM".equals(u.get("bizCd"))) { bizCd = "CM"; break; }
                }
            }
            if (bizCd == null && !units.isEmpty()) bizCd = str(units.get(0).get("bizCd"));
        }
        // 레거시: detailCd만 오면 업무코드로 환원
        if (bizCd == null && detailCd != null) {
            for (Map<String, Object> d : allDetails) {
                if (detailCd.equals(d.get("detailCd"))) {
                    bizCd = str(d.get("bizCd"));
                    if (d.get("domainCd") != null) domainCd = str(d.get("domainCd"));
                    break;
                }
            }
        }

        List<Map<String, Object>> details = new ArrayList<>();
        for (Map<String, Object> d : allDetails) {
            if (bizCd != null && bizCd.equals(d.get("bizCd"))) details.add(d);
        }

        Map<String, Object> appParam = new HashMap<>();
        put(appParam, "systemId", systemId);
        put(appParam, "appKeyword", input == null ? null : input.getAppKeyword());
        List<Map<String, Object>> apps = mapList(dao.ifina2400S0_apps(appParam), this::mapApp);

        Map<String, Object> bamBase = new HashMap<>(base);
        put(bamBase, "domainCd", domainCd);
        List<Map<String, Object>> allBizAppMaps = mapList(dao.ifina2400S0_bizAppMaps(bamBase), this::mapBizApp);
        List<Map<String, Object>> bizAppMaps = new ArrayList<>();
        for (Map<String, Object> b : allBizAppMaps) {
            if (bizCd != null && bizCd.equals(b.get("bizCd"))) bizAppMaps.add(b);
        }

        if (appId == null) {
            for (Map<String, Object> b : bizAppMaps) {
                if ("Y".equals(b.get("primaryYn"))) { appId = str(b.get("appId")); break; }
            }
            if (appId == null && !bizAppMaps.isEmpty()) appId = str(bizAppMaps.get(0).get("appId"));
            if (appId == null) {
                for (Map<String, Object> a : apps) {
                    if ("APP-MK-CUST".equals(a.get("appId"))) { appId = "APP-MK-CUST"; break; }
                }
            }
            if (appId == null) {
                for (Map<String, Object> a : apps) {
                    if ("APP-MK-CAMP".equals(a.get("appId"))) { appId = "APP-MK-CAMP"; break; }
                }
            }
            if (appId == null && !apps.isEmpty()) appId = str(apps.get(0).get("appId"));
        }

        List<Map<String, Object>> maps = new ArrayList<>();
        List<Map<String, Object>> mwRows = new ArrayList<>();
        List<Map<String, Object>> dbRows = new ArrayList<>();
        Map<String, Object> selectedApp = new LinkedHashMap<>();
        Map<String, Object> appRuntime = new LinkedHashMap<>();
        if (appId != null) {
            Map<String, Object> mapParam = Map.of("appId", appId);
            maps = mapList(dao.ifina2400S0_maps(mapParam), this::mapMap);
            mwRows = mapList(dao.ifina2400S0_mw(mapParam), this::mapMw);
            dbRows = mapList(dao.ifina2400S0_db(mapParam), this::mapDb);
            for (Map<String, Object> a : apps) {
                if (appId.equals(a.get("appId"))) {
                    selectedApp = new LinkedHashMap<>(a);
                    break;
                }
            }
            if (selectedApp.isEmpty()) {
                Map<String, Object> raw = dao.ifina2400S0_app(mapParam);
                if (raw != null) selectedApp = mapApp(raw);
                else selectedApp.put("appId", appId);
            }
            appRuntime = buildAppRuntime(appId, selectedApp, maps, mwRows, dbRows);
        }

        Map<String, Object> selectedBiz = buildSelectedBiz(systemId, systems, domainCd, domains, bizCd, units, detailCd, details);
        Map<String, Object> bizTree = buildUnitTree(units, allDetails, countByBiz);
        List<Map<String, Object>> previewLines = buildDetailPreview(selectedBiz, bizAppMaps);
        List<Map<String, Object>> overviewUnits = buildOverviewUnits(units, allDetails, countByBiz, allBizAppMaps);
        List<Map<String, Object>> mapSummary = buildMapSummary(selectedBiz, bizAppMaps);
        List<Map<String, Object>> resultSummary = buildResultSummary(selectedBiz, bizAppMaps, appRuntime);

        Map<String, Object> session = new LinkedHashMap<>();
        if (appId != null && bizCd != null) {
            Map<String, Object> sessParam = new HashMap<>();
            sessParam.put("systemId", systemId);
            sessParam.put("bizCd", bizCd);
            sessParam.put("appId", appId);
            sessParam.put("envCd", envCd);
            Map<String, Object> raw = dao.ifina2400S0_session(sessParam);
            if (raw != null && !raw.isEmpty()) session = mapSession(raw);
            else {
                session.put("systemId", systemId);
                session.put("bizCd", bizCd);
                session.put("appId", appId);
                session.put("envCd", envCd);
                session.put("statusCd", "DRAFT");
            }
        }

        ifina2400S0DTOout out = new ifina2400S0DTOout();
        out.setSystems(systems);
        out.setDomains(domains);
        out.setUnits(units);
        out.setDetails(details);
        out.setApps(apps);
        out.setGroups(List.of());
        out.setServers(List.of());
        out.setDatabases(dbRows);
        out.setMiddlewares(mwRows);
        out.setMaps(maps);
        out.setBizAppMaps(bizAppMaps);
        out.setSelectedApp(selectedApp);
        out.setSelectedBiz(selectedBiz);
        out.setAppRuntime(appRuntime);
        out.setBizTree(bizTree);
        out.setPreviewLines(previewLines);
        out.setOverviewUnits(overviewUnits);
        out.setMapSummary(mapSummary);
        out.setResultSummary(resultSummary);
        out.setSession(session);
        out.setSelectedBiz(selectedBiz);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    public ifina2400C0DTOout ifina2400C0(ifina2400C0DTOin input) throws Exception {
        ifina2400C0DTOout out = new ifina2400C0DTOout();
        if (authGuard.denyIfHard(out, "ifina2400C0")) return out;

        String linkType = blank(input == null ? null : input.getLinkType(), "BIZ_APP").toUpperCase(Locale.ROOT);
        if (!"BIZ_APP".equals(linkType)) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("INF-240 supports BIZ_APP only (infra mapping is INF-230)");
            return out;
        }

        String systemId = trim(input.getSystemId());
        String domainCd = trim(input.getDomainCd());
        String bizCd = trim(input.getBizCd());
        String detailCd = trim(input.getDetailCd()); // optional legacy
        String appId = trim(input.getAppId());
        String envCd = blank(input.getEnvCd(), "PROD");
        if (bizCd == null && detailCd != null && detailCd.contains("-")) {
            bizCd = detailCd.substring(0, detailCd.indexOf('-'));
        }
        if (systemId == null || domainCd == null || bizCd == null || appId == null) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("REQUIRED: systemId, domainCd, bizCd, appId");
            return out;
        }

        Map<String, Object> app = dao.ifina2400S0_app(Map.of("appId", appId));
        if (app == null || app.isEmpty()) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("APP_NOT_FOUND: " + appId);
            return out;
        }
        String appSystem = as(app, "SYSTEM_ID", "systemId");
        if (!systemId.equals(appSystem)) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0003");
            out.setRSLT_MSG("SYSTEM_MISMATCH: app.systemId=" + appSystem);
            return out;
        }

        Map<String, Object> p = new HashMap<>();
        p.put("systemId", systemId);
        p.put("bizCd", bizCd);
        p.put("appId", appId);
        p.put("envCd", envCd);
        if (dao.ifina2400C0_bizAppExists(p) > 0) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0002");
            out.setRSLT_MSG("ALREADY_MAPPED");
            return out;
        }

        String mapRole = blank(input.getMapRoleCd(), "PRIMARY");
        String primaryYn = blank(input.getPrimaryYn(), "Y");
        Map<String, Object> pri = new HashMap<>();
        pri.put("systemId", systemId);
        pri.put("bizCd", bizCd);
        pri.put("envCd", envCd);
        boolean hasPrimary = dao.ifina2400U0_countPrimaryBizApp(pri) > 0;
        // 다중 App 매핑: Primary가 이미 있으면 자동으로 보조(N)
        if ("Y".equalsIgnoreCase(primaryYn) && hasPrimary) {
            primaryYn = "N";
        }
        if (!hasPrimary) {
            primaryYn = "Y";
        }

        p.put("bizAppMapId", "BAM-" + System.currentTimeMillis() + "-" + (int) (Math.random() * 900 + 100));
        p.put("domainCd", domainCd);
        p.put("detailCd", null);
        p.put("mapRoleCd", mapRole);
        p.put("primaryYn", primaryYn);
        p.put("statusCd", "DRAFT");
        p.put("remark", empty(input.getRemark()));
        p.put("regUserId", blank(input.getRegUserId(), "LOCAL"));
        p.put("regDtm", now());
        int n = dao.ifina2400C0_bizAppInsert(p);
        changeLogWriter.write("BIZ_APP_MAP", str(p.get("bizAppMapId")), "CREATE", null, p, "ifina2400C0");
        out.setPROC_CNT(n);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    public ifina2400U0DTOout ifina2400U0(ifina2400U0DTOin input) throws Exception {
        ifina2400U0DTOout out = new ifina2400U0DTOout();
        if (authGuard.denyIfHard(out, "ifina2400U0")) return out;

        String systemId = trim(input == null ? null : input.getSystemId());
        String bizCd = trim(input == null ? null : input.getBizCd());
        String detailCd = trim(input == null ? null : input.getDetailCd());
        String appId = trim(input == null ? null : input.getAppId());
        String envCd = blank(input == null ? null : input.getEnvCd(), "PROD");
        String action = blank(input == null ? null : input.getAction(), "DRAFT").toUpperCase(Locale.ROOT);
        String chgUserId = blank(input == null ? null : input.getChgUserId(), "LOCAL");
        if (bizCd == null && detailCd != null && detailCd.contains("-")) {
            bizCd = detailCd.substring(0, detailCd.indexOf('-'));
        }

        if (systemId == null || bizCd == null || appId == null) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("REQUIRED: systemId, bizCd, appId");
            return out;
        }
        if (!Set.of("DRAFT", "VALIDATE", "CONFIRM", "CANCEL").contains(action)) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("INVALID action: " + action);
            return out;
        }

        List<String> warnings = new ArrayList<>();
        String statusCd;
        switch (action) {
            case "VALIDATE" -> {
                String err = validateBizApp(systemId, bizCd, appId, envCd, warnings);
                if (err != null) {
                    out.setPROC_CNT(0);
                    out.setRSLT_CD("0003");
                    out.setRSLT_MSG(err);
                    out.setWarnings(warnings);
                    return out;
                }
                statusCd = "VALIDATED";
            }
            case "CONFIRM" -> {
                String err = validateBizApp(systemId, bizCd, appId, envCd, warnings);
                if (err != null) {
                    out.setPROC_CNT(0);
                    out.setRSLT_CD("0003");
                    out.setRSLT_MSG(err);
                    out.setWarnings(warnings);
                    return out;
                }
                statusCd = "CONFIRMED";
            }
            case "CANCEL" -> statusCd = "DRAFT";
            default -> statusCd = "DRAFT";
        }

        Map<String, Object> p = new HashMap<>();
        p.put("systemId", systemId);
        p.put("bizCd", bizCd);
        p.put("appId", appId);
        p.put("envCd", envCd);
        p.put("statusCd", statusCd);
        p.put("chgUserId", chgUserId);
        p.put("chgDtm", now());
        p.put("remark", empty(input == null ? null : input.getRemark()));

        int n;
        if (dao.ifina2400U0_sessionExists(p) > 0) n = dao.ifina2400U0_updateSession(p);
        else n = dao.ifina2400U0_insertSession(p);
        if (dao.ifina2400C0_bizAppExists(p) > 0) n += dao.ifina2400U0_updateBizAppStatus(p);
        changeLogWriter.write("MAP_SESSION", systemId + "/" + bizCd + "/" + appId + "/" + envCd,
                action, null, p, "ifina2400U0");

        out.setPROC_CNT(n);
        out.setStatusCd(statusCd);
        out.setWarnings(warnings);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG(warnings.isEmpty() ? "OK" : "OK_WITH_WARNINGS");
        return out;
    }

    public ifina2400D0DTOout ifina2400D0(ifina2400D0DTOin input) throws Exception {
        ifina2400D0DTOout out = new ifina2400D0DTOout();
        if (authGuard.denyIfHard(out, "ifina2400D0")) return out;

        String unlinkType = blank(input == null ? null : input.getUnlinkType(), "BIZ_APP").toUpperCase(Locale.ROOT);
        if (!"BIZ_APP".equals(unlinkType)) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("INF-240 supports BIZ_APP unlink only");
            return out;
        }
        String systemId = trim(input.getSystemId());
        String bizCd = trim(input.getBizCd());
        String detailCd = trim(input.getDetailCd());
        String appId = trim(input.getAppId());
        String envCd = blank(input.getEnvCd(), "PROD");
        if (bizCd == null && detailCd != null && detailCd.contains("-")) {
            bizCd = detailCd.substring(0, detailCd.indexOf('-'));
        }
        if (systemId == null || bizCd == null || appId == null) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("REQUIRED: systemId, bizCd, appId");
            return out;
        }
        Map<String, Object> p = new HashMap<>();
        p.put("systemId", systemId);
        p.put("bizCd", bizCd);
        p.put("appId", appId);
        p.put("envCd", envCd);
        int n = dao.ifina2400D0_bizAppDelete(p);
        changeLogWriter.write("BIZ_APP_MAP", systemId + "/" + bizCd + "/" + appId, "DELETE", p, null, "ifina2400D0");
        out.setPROC_CNT(n);
        out.setRSLT_CD(n > 0 ? "0000" : "0004");
        out.setRSLT_MSG(n > 0 ? "OK" : "NOT_FOUND");
        return out;
    }

    private String validateBizApp(String systemId, String bizCd, String appId, String envCd,
                                  List<String> warnings) throws Exception {
        Map<String, Object> key = new HashMap<>();
        key.put("systemId", systemId);
        key.put("bizCd", bizCd);
        key.put("appId", appId);
        key.put("envCd", envCd);
        if (dao.ifina2400C0_bizAppExists(key) < 1) {
            return "BIZ_APP_MAP_REQUIRED";
        }
        Map<String, Object> app = dao.ifina2400S0_app(Map.of("appId", appId));
        if (app == null || app.isEmpty()) return "APP_NOT_FOUND";
        if (!systemId.equals(as(app, "SYSTEM_ID", "systemId"))) {
            return "SYSTEM_MISMATCH";
        }
        Map<String, Object> pri = new HashMap<>();
        pri.put("systemId", systemId);
        pri.put("bizCd", bizCd);
        pri.put("envCd", envCd);
        if (dao.ifina2400U0_countPrimaryBizApp(pri) > 1) {
            return "PRIMARY_DUPLICATE";
        }
        if (dao.ifina2400U0_countDeletedServerMaps(Map.of("appId", appId)) > 0) {
            return "DELETED_SERVER_IN_USE";
        }
        int mapCnt = dao.ifina2400U0_countMaps(Map.of("appId", appId));
        if (mapCnt < 1) warnings.add("INFRA_MAP_MISSING");
        List<Map<String, Object>> dbs = dao.ifina2400S0_db(Map.of("appId", appId));
        if (dbs == null || dbs.isEmpty()) warnings.add("DB_NOT_LINKED");
        List<Map<String, Object>> mws = dao.ifina2400S0_mw(Map.of("appId", appId));
        if (mws == null || mws.isEmpty()) warnings.add("MW_NOT_CONFIRMED");
        return null;
    }

    private Map<String, Object> buildUnitTree(List<Map<String, Object>> units,
                                              List<Map<String, Object>> allDetails,
                                              Map<String, Map<String, Object>> countByBiz) {
        Map<String, Object> tree = new LinkedHashMap<>();
        List<Map<String, Object>> unitNodes = new ArrayList<>();
        for (Map<String, Object> unit : units) {
            String bizCd = str(unit.get("bizCd"));
            Map<String, Object> cnt = countByBiz.getOrDefault(bizCd, Map.of());
            int appCnt = toInt(cnt.get("appCnt"));
            int doneCnt = toInt(cnt.get("doneCnt"));
            Map<String, Object> uNode = new LinkedHashMap<>();
            uNode.put("bizCd", bizCd);
            uNode.put("nameKo", unit.get("nameKo"));
            uNode.put("nameEn", unit.get("nameEn"));
            List<Map<String, Object>> detailNodes = new ArrayList<>();
            for (Map<String, Object> det : allDetails) {
                if (bizCd == null || !bizCd.equals(det.get("bizCd"))) continue;
                Map<String, Object> detNode = new LinkedHashMap<>(det);
                detNode.put("appCnt", 0);
                detNode.put("doneCnt", 0);
                detNode.put("mapStatus", "NONE");
                detailNodes.add(detNode);
            }
            uNode.put("details", detailNodes);
            uNode.put("appCnt", appCnt);
            uNode.put("doneCnt", doneCnt);
            uNode.put("mappedCnt", appCnt > 0 ? 1 : 0);
            uNode.put("detailCnt", detailNodes.size());
            uNode.put("mapStatus", mapStatusOf(appCnt, doneCnt));
            unitNodes.add(uNode);
        }
        tree.put("units", unitNodes);
        return tree;
    }

    private Map<String, Object> buildSelectedBiz(String systemId, List<Map<String, Object>> systems,
                                                 String domainCd, List<Map<String, Object>> domains,
                                                 String bizCd, List<Map<String, Object>> units,
                                                 String detailCd, List<Map<String, Object>> details) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("systemId", systemId);
        m.put("systemName", findName(systems, "systemId", systemId, "systemName"));
        m.put("domainCd", domainCd);
        m.put("domainName", findName(domains, "domainCd", domainCd, "nameKo"));
        m.put("domainNameEn", findName(domains, "domainCd", domainCd, "nameEn"));
        m.put("bizCd", bizCd);
        m.put("bizName", findName(units, "bizCd", bizCd, "nameKo"));
        m.put("detailCnt", details == null ? 0 : details.size());

        Map<String, Object> detail = null;
        if (details != null) {
            if (detailCd != null) {
                for (Map<String, Object> d : details) {
                    if (detailCd.equals(d.get("detailCd"))) { detail = d; break; }
                }
            }
            if (detail == null && !details.isEmpty()) detail = details.get(0);
        }
        if (detail != null) {
            m.put("detailCd", detail.get("detailCd"));
            m.put("detailName", detail.get("nameKo"));
            m.put("functionCd", detail.get("functionCd") != null ? detail.get("functionCd") : detailShort(str(detail.get("detailCd"))));
            m.put("detailShortCd", m.get("functionCd"));
            m.put("mappingKey", nvl(domainCd) + "/" + nvl(bizCd) + "/" + nvl(detail.get("detailCd")));
        } else {
            m.put("mappingKey", nvl(domainCd) + "/" + nvl(bizCd));
        }
        return m;
    }

    private Map<String, Object> buildAppRuntime(String appId, Map<String, Object> selectedApp,
                                                List<Map<String, Object>> maps,
                                                List<Map<String, Object>> mwRows,
                                                List<Map<String, Object>> dbRows) throws Exception {
        Map<String, Object> runtime = new LinkedHashMap<>();
        runtime.put("appId", appId);
        runtime.put("appName", selectedApp.get("appName"));
        runtime.put("appTypeCd", selectedApp.get("appTypeCd"));
        Map<String, Map<String, Object>> groupMap = new LinkedHashMap<>();
        List<Map<String, Object>> servers = new ArrayList<>();
        Set<String> serverIds = new HashSet<>();
        for (Map<String, Object> m : maps) {
            String type = str(m.get("mapTypeCd"));
            if ("GROUP".equals(type)) {
                String gid = str(m.get("refId"));
                if (gid == null) continue;
                Map<String, Object> g = groupMap.computeIfAbsent(gid, k -> {
                    Map<String, Object> n = new LinkedHashMap<>();
                    n.put("groupId", k);
                    n.put("groupName", m.get("refName"));
                    n.put("techRoleCd", m.get("roleCd"));
                    n.put("servers", new ArrayList<Map<String, Object>>());
                    return n;
                });
                List<Map<String, Object>> assets = mapList(dao.ifina2400S0_assetsByGroup(Map.of("groupId", gid)), this::mapServer);
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> srv = (List<Map<String, Object>>) g.get("servers");
                for (Map<String, Object> a : assets) {
                    String aid = str(a.get("assetId"));
                    if (aid == null || !serverIds.add(aid)) continue;
                    Map<String, Object> s = new LinkedHashMap<>();
                    s.put("assetId", aid);
                    s.put("assetName", a.get("assetName"));
                    s.put("ipAddr", a.get("ipAddr"));
                    s.put("portNo", a.get("portNo"));
                    s.put("groupId", gid);
                    s.put("statusCd", a.get("statusCd"));
                    srv.add(s);
                    servers.add(s);
                }
            } else if ("SERVER".equals(type)) {
                String gid = str(m.get("groupId"));
                if (gid == null || gid.isBlank()) gid = "-";
                Map<String, Object> g = groupMap.computeIfAbsent(gid, k -> {
                    Map<String, Object> n = new LinkedHashMap<>();
                    n.put("groupId", k);
                    n.put("groupName", k);
                    n.put("techRoleCd", m.get("roleCd"));
                    n.put("servers", new ArrayList<Map<String, Object>>());
                    return n;
                });
                String aid = str(m.get("refId"));
                if (aid != null && serverIds.add(aid)) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> srv = (List<Map<String, Object>>) g.get("servers");
                    Map<String, Object> s = new LinkedHashMap<>();
                    s.put("assetId", aid);
                    s.put("assetName", m.get("serverName") != null ? m.get("serverName") : m.get("refName"));
                    s.put("roleCd", m.get("roleCd"));
                    s.put("primaryYn", m.get("primaryYn"));
                    s.put("groupId", gid);
                    // IP from asset lookup if available
                    try {
                        Map<String, Object> asset = dao.ifina2400S0_asset(Map.of("assetId", aid));
                        if (asset != null) {
                            List<Map<String, Object>> withIp = mapList(
                                    dao.ifina2400S0_assetsByGroup(Map.of("groupId",
                                            as(asset, "GROUP_ID", "groupId") != null ? as(asset, "GROUP_ID", "groupId") : gid)),
                                    this::mapServer);
                            for (Map<String, Object> a : withIp) {
                                if (aid.equals(a.get("assetId"))) {
                                    s.put("ipAddr", a.get("ipAddr"));
                                    s.put("portNo", a.get("portNo"));
                                    s.put("statusCd", a.get("statusCd"));
                                    break;
                                }
                            }
                        }
                    } catch (Exception ignored) { /* keep without IP */ }
                    srv.add(s);
                    servers.add(s);
                }
            }
        }
        // enrich group techRole from first map role / group name heuristic
        for (Map<String, Object> g : groupMap.values()) {
            if (g.get("techRoleCd") == null || String.valueOf(g.get("techRoleCd")).isBlank()) {
                String gn = str(g.get("groupName"));
                String gid = str(g.get("groupId"));
                String src = (gn != null ? gn : "") + " " + (gid != null ? gid : "");
                String u = src.toUpperCase(Locale.ROOT);
                if (u.contains("WEB")) g.put("techRoleCd", "WEB");
                else if (u.contains("WAS")) g.put("techRoleCd", "WAS");
                else if (u.contains("BATCH")) g.put("techRoleCd", "BATCH");
                else if (u.contains("DB")) g.put("techRoleCd", "DATABASE");
            }
        }
        List<Map<String, Object>> networks = List.of();
        try {
            networks = mapList(dao.ifina2400S0_networkByApp(Map.of("appId", appId)), this::mapNetwork);
        } catch (Exception ignored) { /* optional */ }
        runtime.put("groups", new ArrayList<>(groupMap.values()));
        runtime.put("servers", servers);
        runtime.put("databases", dbRows);
        runtime.put("middlewares", mwRows);
        runtime.put("networks", networks);
        runtime.put("maps", maps);
        runtime.put("serverCount", servers.size());
        runtime.put("groupCount", groupMap.size());
        runtime.put("dbLinked", dbRows != null && !dbRows.isEmpty());
        return runtime;
    }

    private List<Map<String, Object>> buildDetailPreview(Map<String, Object> selectedBiz,
                                                         List<Map<String, Object>> bizAppMaps) throws Exception {
        List<Map<String, Object>> lines = new ArrayList<>();
        lines.add(line(0, nvl(selectedBiz.get("systemId")) + " " + nvl(selectedBiz.get("systemName"))));
        lines.add(line(1, nvl(selectedBiz.get("domainCd")) + " " + nvl(selectedBiz.get("domainName"))));
        lines.add(line(2, nvl(selectedBiz.get("bizCd")) + " " + nvl(selectedBiz.get("bizName"))
                + " (세부 " + nvl(selectedBiz.get("detailCnt")) + ")"));
        for (Map<String, Object> bam : bizAppMaps) {
            String appId = str(bam.get("appId"));
            lines.add(line(3, appId + " " + nvl(bam.get("appName"))));
            List<Map<String, Object>> maps = mapList(dao.ifina2400S0_maps(Map.of("appId", appId)), this::mapMap);
            List<Map<String, Object>> mwRows = mapList(dao.ifina2400S0_mw(Map.of("appId", appId)), this::mapMw);
            List<Map<String, Object>> dbRows = mapList(dao.ifina2400S0_db(Map.of("appId", appId)), this::mapDb);
            Map<String, Object> rt = buildAppRuntime(appId, bam, maps, mwRows, dbRows);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> groups = (List<Map<String, Object>>) rt.get("groups");
            if (groups != null) {
                for (Map<String, Object> g : groups) {
                    lines.add(line(4, str(g.get("groupId"))));
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> srv = (List<Map<String, Object>>) g.get("servers");
                    if (srv != null) {
                        for (Map<String, Object> s : srv) {
                            lines.add(line(5, nvl(s.get("assetId")) + " / " + nvl(s.get("assetName"))));
                        }
                    }
                }
            }
            for (Map<String, Object> mw : mwRows) {
                lines.add(line(4, "MW : " + nvl(mw.get("productName")) + " " + nvl(mw.get("versionNo"))));
            }
            for (Map<String, Object> db : dbRows) {
                lines.add(line(4, "DB : " + nvl(db.get("dbId")) + " / " + nvl(db.get("dbName"))
                        + " / " + nvl(db.get("engineCd")) + " " + nvl(db.get("versionNo"))));
            }
        }
        if (bizAppMaps.isEmpty()) {
            lines.add(line(3, "(매핑 없음 — Application 연결 필요)"));
        }
        return lines;
    }

    private List<Map<String, Object>> buildMapSummary(Map<String, Object> selectedBiz,
                                                      List<Map<String, Object>> bizAppMaps) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Map<String, Object> bam : bizAppMaps) {
            String appId = str(bam.get("appId"));
            List<Map<String, Object>> maps = mapList(dao.ifina2400S0_maps(Map.of("appId", appId)), this::mapMap);
            List<Map<String, Object>> dbRows = mapList(dao.ifina2400S0_db(Map.of("appId", appId)), this::mapDb);
            Map<String, Object> rt = buildAppRuntime(appId, bam, maps, List.of(), dbRows);
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("bizCd", selectedBiz.get("bizCd"));
            row.put("bizName", selectedBiz.get("bizName"));
            row.put("appId", appId);
            row.put("appName", bam.get("appName"));
            row.put("mapRoleCd", bam.get("mapRoleCd"));
            row.put("primaryYn", bam.get("primaryYn"));
            row.put("groupCount", rt.get("groupCount"));
            row.put("serverCount", rt.get("serverCount"));
            row.put("dbId", dbRows.isEmpty() ? null : dbRows.get(0).get("dbId"));
            row.put("statusCd", bam.get("statusCd"));
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> groups = (List<Map<String, Object>>) rt.get("groups");
            row.put("groupId", (groups != null && !groups.isEmpty()) ? groups.get(0).get("groupId") : null);
            rows.add(row);
        }
        return rows;
    }

    private List<Map<String, Object>> buildOverviewUnits(List<Map<String, Object>> units,
                                                         List<Map<String, Object>> allDetails,
                                                         Map<String, Map<String, Object>> countByBiz,
                                                         List<Map<String, Object>> bizAppMaps) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (Map<String, Object> unit : units) {
            String bizCd = str(unit.get("bizCd"));
            Map<String, Object> cnt = countByBiz.getOrDefault(bizCd, Map.of());
            int appCnt = toInt(cnt.get("appCnt"));
            int doneCnt = toInt(cnt.get("doneCnt"));
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("bizCd", bizCd);
            row.put("nameKo", unit.get("nameKo"));
            List<Map<String, Object>> details = new ArrayList<>();
            for (Map<String, Object> det : allDetails) {
                if (bizCd == null || !bizCd.equals(det.get("bizCd"))) continue;
                Map<String, Object> d = new LinkedHashMap<>(det);
                d.put("appCnt", 0);
                d.put("mapStatus", "NONE");
                d.put("apps", List.of());
                details.add(d);
            }
            List<Map<String, Object>> linked = new ArrayList<>();
            for (Map<String, Object> bam : bizAppMaps) {
                if (bizCd != null && bizCd.equals(bam.get("bizCd"))) {
                    linked.add(Map.of(
                            "appId", nvl(bam.get("appId")),
                            "statusCd", nvl(bam.get("statusCd"))
                    ));
                }
            }
            row.put("details", details);
            row.put("apps", linked);
            row.put("appCnt", appCnt);
            row.put("doneCnt", doneCnt);
            row.put("mappedCnt", appCnt > 0 ? 1 : 0);
            row.put("detailCnt", details.size());
            row.put("mapStatus", mapStatusOf(appCnt, doneCnt));
            row.put("progressText", appCnt + " App" + (doneCnt > 0 ? " / " + doneCnt + " 확정" : ""));
            out.add(row);
        }
        return out;
    }

    private static String mapStatusOf(int appCnt, int doneCnt) {
        if (appCnt <= 0) return "NONE";
        if (doneCnt >= appCnt) return "DONE";
        return "PARTIAL";
    }

    private static int toInt(Object v) {
        if (v == null) return 0;
        if (v instanceof Number n) return n.intValue();
        try { return Integer.parseInt(String.valueOf(v)); } catch (Exception e) { return 0; }
    }

    private static Map<String, Object> line(int depth, String text) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("depth", depth);
        m.put("text", text);
        return m;
    }

    private String findName(List<Map<String, Object>> rows, String idKey, String id, String nameKey) {
        if (id == null || rows == null) return "";
        for (Map<String, Object> r : rows) {
            if (id.equals(r.get(idKey))) return str(r.get(nameKey));
        }
        return "";
    }

    private List<Map<String, Object>> mapList(List<Map<String, Object>> raw,
                                              java.util.function.Function<Map<String, Object>, Map<String, Object>> fn) {
        List<Map<String, Object>> out = new ArrayList<>();
        if (raw == null) return out;
        for (Map<String, Object> row : raw) out.add(fn.apply(row));
        return out;
    }

    private Map<String, Object> mapSystem(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("systemId", as(row, "SYSTEM_ID", "systemId"));
        m.put("systemName", as(row, "SYSTEM_NAME", "systemName"));
        m.put("statusCd", as(row, "STATUS_CD", "statusCd"));
        return m;
    }

    private Map<String, Object> mapDomain(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("domainCd", as(row, "DOMAIN_CD", "domainCd"));
        m.put("systemId", as(row, "SYSTEM_ID", "systemId"));
        m.put("nameKo", as(row, "NAME_KO", "nameKo"));
        m.put("nameEn", as(row, "NAME_EN", "nameEn"));
        return m;
    }

    private Map<String, Object> mapUnit(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("bizCd", as(row, "BIZ_CD", "bizCd"));
        m.put("domainCd", as(row, "DOMAIN_CD", "domainCd"));
        m.put("nameKo", as(row, "NAME_KO", "nameKo"));
        m.put("nameEn", as(row, "NAME_EN", "nameEn"));
        return m;
    }

    private Map<String, Object> mapDetailFull(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        String detailCd = as(row, "DETAIL_CD", "detailCd");
        m.put("detailCd", detailCd);
        m.put("bizCd", as(row, "BIZ_CD", "bizCd"));
        m.put("domainCd", as(row, "DOMAIN_CD", "domainCd"));
        m.put("nameKo", as(row, "NAME_KO", "nameKo"));
        m.put("bizName", as(row, "BIZ_NAME", "bizName"));
        String functionCd = as(row, "FUNCTION_CD", "functionCd");
        if (functionCd == null || functionCd.isBlank()) functionCd = detailShort(detailCd);
        m.put("functionCd", functionCd);
        m.put("detailShortCd", functionCd);
        return m;
    }

    private Map<String, Object> mapCount(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("bizCd", as(row, "BIZ_CD", "bizCd"));
        m.put("appCnt", row.get("APP_CNT") != null ? row.get("APP_CNT") : row.get("appCnt"));
        m.put("doneCnt", row.get("DONE_CNT") != null ? row.get("DONE_CNT") : row.get("doneCnt"));
        m.put("draftCnt", row.get("DRAFT_CNT") != null ? row.get("DRAFT_CNT") : row.get("draftCnt"));
        return m;
    }

    private Map<String, Object> mapBizApp(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("bizAppMapId", as(row, "BIZ_APP_MAP_ID", "bizAppMapId"));
        m.put("systemId", as(row, "SYSTEM_ID", "systemId"));
        m.put("detailCd", as(row, "DETAIL_CD", "detailCd"));
        m.put("appId", as(row, "APP_ID", "appId"));
        m.put("envCd", as(row, "ENV_CD", "envCd"));
        m.put("domainCd", as(row, "DOMAIN_CD", "domainCd"));
        m.put("bizCd", as(row, "BIZ_CD", "bizCd"));
        m.put("mapRoleCd", as(row, "MAP_ROLE_CD", "mapRoleCd"));
        m.put("primaryYn", as(row, "PRIMARY_YN", "primaryYn"));
        m.put("statusCd", as(row, "STATUS_CD", "statusCd"));
        m.put("remark", as(row, "REMARK", "remark"));
        m.put("appName", as(row, "APP_NAME", "appName"));
        m.put("appTypeCd", as(row, "APP_TYPE_CD", "appTypeCd"));
        m.put("detailName", as(row, "DETAIL_NAME", "detailName"));
        m.put("functionCd", as(row, "FUNCTION_CD", "functionCd"));
        m.put("bizName", as(row, "BIZ_NAME", "bizName"));
        return m;
    }

    private Map<String, Object> mapApp(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("appId", as(row, "APP_ID", "appId"));
        m.put("appName", as(row, "APP_NAME", "appName"));
        m.put("systemId", as(row, "SYSTEM_ID", "systemId"));
        m.put("detailCd", as(row, "DETAIL_CD", "detailCd"));
        String typeCsv = as(row, "APP_TYPE_CD", "appTypeCd");
        m.put("appTypeCd", typeCsv);
        m.put("appTypeList", splitAppTypes(typeCsv));
        m.put("statusCd", as(row, "STATUS_CD", "statusCd"));
        m.put("langCd", as(row, "LANG_CD", "langCd"));
        m.put("remark", as(row, "REMARK", "remark"));
        return m;
    }

    private List<Map<String, Object>> buildResultSummary(Map<String, Object> selectedBiz,
                                                         List<Map<String, Object>> bizAppMaps,
                                                         Map<String, Object> appRuntime) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<>();
        int groupCnt = 0, serverCnt = 0;
        boolean dbLinked = false;
        Set<String> groupIds = new HashSet<>();
        Set<String> serverIds = new HashSet<>();
        for (Map<String, Object> bam : bizAppMaps) {
            String appId = str(bam.get("appId"));
            List<Map<String, Object>> maps = mapList(dao.ifina2400S0_maps(Map.of("appId", appId)), this::mapMap);
            List<Map<String, Object>> dbRows = mapList(dao.ifina2400S0_db(Map.of("appId", appId)), this::mapDb);
            Map<String, Object> rt = buildAppRuntime(appId, bam, maps, List.of(), dbRows);
            if (Boolean.TRUE.equals(rt.get("dbLinked"))) dbLinked = true;
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> groups = (List<Map<String, Object>>) rt.get("groups");
            if (groups != null) {
                for (Map<String, Object> g : groups) {
                    String gid = str(g.get("groupId"));
                    if (gid != null) groupIds.add(gid);
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> srv = (List<Map<String, Object>>) g.get("servers");
                    if (srv != null) {
                        for (Map<String, Object> s : srv) {
                            String aid = str(s.get("assetId"));
                            if (aid != null) serverIds.add(aid);
                        }
                    }
                }
            }
        }
        groupCnt = groupIds.size();
        serverCnt = serverIds.size();
        int appCnt = bizAppMaps == null ? 0 : bizAppMaps.size();
        int confirmed = 0;
        if (bizAppMaps != null) {
            for (Map<String, Object> b : bizAppMaps) {
                if ("CONFIRMED".equals(b.get("statusCd"))) confirmed++;
            }
        }
        String st = appCnt <= 0 ? "NONE" : (confirmed >= appCnt && dbLinked ? "DONE" : (confirmed > 0 || appCnt > 0 ? "PARTIAL" : "NONE"));
        if (appCnt > 0 && confirmed >= appCnt) st = dbLinked ? "DONE" : "PARTIAL";

        Map<String, Object> row = new LinkedHashMap<>();
        String detailLabel;
        if (selectedBiz.get("detailCd") != null) {
            detailLabel = nvl(selectedBiz.get("functionCd")) + " " + nvl(selectedBiz.get("detailName"))
                    + " (" + nvl(selectedBiz.get("detailCd")) + ")";
        } else {
            detailLabel = nvl(selectedBiz.get("bizCd")) + " " + nvl(selectedBiz.get("bizName"));
        }
        row.put("detailLabel", detailLabel);
        row.put("detailCd", selectedBiz.get("detailCd"));
        row.put("bizCd", selectedBiz.get("bizCd"));
        row.put("appCnt", appCnt);
        row.put("groupCnt", groupCnt);
        row.put("serverCnt", serverCnt);
        row.put("dbLinked", dbLinked);
        row.put("dbLabel", dbLinked ? "연결됨" : (appCnt > 0 ? "미연결" : "-"));
        row.put("mapStatus", st);
        row.put("statusLabel", "DONE".equals(st) ? "매핑완료" : ("PARTIAL".equals(st) ? "부분매핑" : "미매핑"));
        rows.add(row);
        return rows;
    }

    private Map<String, Object> mapServer(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("assetId", as(row, "ASSET_ID", "assetId"));
        m.put("assetName", as(row, "ASSET_NAME", "assetName"));
        m.put("groupId", as(row, "GROUP_ID", "groupId"));
        m.put("techRoleCd", as(row, "TECH_ROLE_CD", "techRoleCd"));
        m.put("statusCd", as(row, "STATUS_CD", "statusCd"));
        m.put("ipAddr", as(row, "IP_ADDR", "ipAddr"));
        m.put("portNo", as(row, "PORT_NO", "portNo"));
        return m;
    }

    private Map<String, Object> mapNetwork(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("endpointId", as(row, "ENDPOINT_ID", "endpointId"));
        m.put("assetId", as(row, "ASSET_ID", "assetId"));
        m.put("assetName", as(row, "ASSET_NAME", "assetName"));
        m.put("endpointTypeCd", as(row, "ENDPOINT_TYPE_CD", "endpointTypeCd"));
        m.put("address", as(row, "ADDRESS", "address"));
        m.put("portNo", as(row, "PORT_NO", "portNo"));
        m.put("protocolCd", as(row, "PROTOCOL_CD", "protocolCd"));
        m.put("primaryYn", as(row, "PRIMARY_YN", "primaryYn"));
        return m;
    }

    private Map<String, Object> mapMap(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("mapId", as(row, "MAP_ID", "mapId"));
        m.put("appId", as(row, "APP_ID", "appId"));
        m.put("mapTypeCd", as(row, "MAP_TYPE_CD", "mapTypeCd"));
        m.put("refId", as(row, "REF_ID", "refId"));
        m.put("refName", as(row, "REF_NAME", "refName"));
        m.put("roleCd", as(row, "ROLE_CD", "roleCd"));
        m.put("primaryYn", as(row, "PRIMARY_YN", "primaryYn"));
        m.put("statusCd", as(row, "STATUS_CD", "statusCd"));
        String gid = as(row, "GROUP_ID", "groupId");
        if (gid == null || gid.isBlank()) gid = as(row, "RESOLVED_GROUP_ID", "resolvedGroupId");
        m.put("groupId", gid);
        if ("SERVER".equals(m.get("mapTypeCd"))) m.put("serverName", as(row, "REF_NAME", "refName"));
        return m;
    }

    private Map<String, Object> mapMw(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("productName", as(row, "PRODUCT_NAME", "productName"));
        m.put("versionNo", as(row, "VERSION_NO", "versionNo"));
        m.put("assetId", as(row, "ASSET_ID", "assetId"));
        m.put("mwId", as(row, "MW_ID", "mwId"));
        return m;
    }

    private Map<String, Object> mapDb(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("dbId", as(row, "DB_ID", "dbId"));
        m.put("dbName", as(row, "DB_NAME", "dbName"));
        m.put("engineCd", as(row, "ENGINE_CD", "engineCd"));
        m.put("versionNo", as(row, "VERSION_NO", "versionNo"));
        return m;
    }

    private Map<String, Object> mapSession(Map<String, Object> row) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("systemId", as(row, "SYSTEM_ID", "systemId"));
        m.put("bizCd", as(row, "BIZ_CD", "bizCd"));
        m.put("appId", as(row, "APP_ID", "appId"));
        m.put("envCd", as(row, "ENV_CD", "envCd"));
        m.put("statusCd", as(row, "STATUS_CD", "statusCd"));
        m.put("chgUserId", as(row, "CHG_USER_ID", "chgUserId"));
        m.put("chgDtm", as(row, "CHG_DTM", "chgDtm"));
        m.put("remark", as(row, "REMARK", "remark"));
        return m;
    }

    private static List<String> splitAppTypes(String raw) {
        List<String> out = new ArrayList<>();
        if (raw == null || raw.isBlank()) return out;
        for (String p : raw.split("[,|/;\\s]+")) {
            String u = p.trim().toUpperCase(Locale.ROOT);
            if (("ONLINE".equals(u) || "BATCH".equals(u) || "UI".equals(u)) && !out.contains(u)) out.add(u);
        }
        return out;
    }

    private static String detailShort(String detailCd) {
        if (detailCd == null || detailCd.isBlank()) return "";
        int idx = detailCd.lastIndexOf('-');
        return idx >= 0 && idx < detailCd.length() - 1 ? detailCd.substring(idx + 1) : detailCd;
    }

    private static String now() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
    }

    private static String trim(String v) {
        if (v == null) return null;
        String t = v.trim();
        return t.isEmpty() ? null : t;
    }

    private static String empty(String v) { return v == null ? "" : v.trim(); }

    private static String blank(String v, String d) {
        String t = trim(v);
        return t != null ? t : d;
    }

    private static void put(Map<String, Object> m, String k, String v) {
        if (v != null && !v.isBlank()) m.put(k, v.trim());
    }

    private static String str(Object v) { return v == null ? null : String.valueOf(v); }

    private static String nvl(Object v) { return v == null ? "" : String.valueOf(v); }

    private static String as(Map<String, Object> row, String u, String c) {
        if (row == null) return null;
        Object v = row.get(u);
        if (v == null) v = row.get(c);
        if (v == null) {
            for (Map.Entry<String, Object> e : row.entrySet()) {
                if (e.getKey() != null && (e.getKey().equalsIgnoreCase(u) || e.getKey().equalsIgnoreCase(c))) {
                    v = e.getValue();
                    break;
                }
            }
        }
        return v == null ? null : String.valueOf(v);
    }
}
