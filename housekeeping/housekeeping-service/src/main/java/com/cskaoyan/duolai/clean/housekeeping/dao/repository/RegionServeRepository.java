package com.cskaoyan.duolai.clean.housekeeping.dao.repository;

import com.cskaoyan.duolai.clean.common.model.RegionServeInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface RegionServeRepository extends ElasticsearchRepository<RegionServeInfo, Long> {


    @Query("""
             {
               "bool" : {
                  "must" : [
                     {
                        "term" : {
                           "cityCode" : {
                              "value" : "?0"
                           }
                        }
                     },
                     {
                        "query_string" : {
                           "fields" : [ "serveItemName", "serveTypeName"],
                           "query" : "?1"
                        }
                     }
                  ]
               }
            }
           """)
    List<RegionServeInfo> searchRegionServeInfo(String cityCode, String keyword, Sort sort);

    @Query("""
             {
               "bool" : {
                  "must" : [
                     {
                        "term" : {
                           "cityCode" : {
                              "value" : "?0"
                           }
                        }
                     },
                     {
                        "term" : {
                           "serveTypeId" :"?1"
                        }
                     }
                  ]
               }
            }
           """)
    List<RegionServeInfo> searchRegionServeInfoByServeType(String cityCode, String typeId, Sort sort);

}
