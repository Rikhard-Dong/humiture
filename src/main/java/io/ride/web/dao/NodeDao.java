package io.ride.web.dao;

import io.ride.web.entity.Node;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-4
 * Time: 下午7:42
 */
public interface NodeDao {
    boolean isExists(@Param("mark") String mark);

    List<Node> list();

    Node findById(int nodeId);

    Node findByMark(@Param("mark") String mark,
                    @Param("userType") Integer userType,
                    @Param("unitId") Integer unitId);

    Node findByIdWithGetway(int nodeId);

    Node findByMarkWithGetway(String mask);

    Node findByUnitIdAndNodeId(@Param("unitId") Integer unitId, @Param("nodeId") Integer nodeId);


    int addNode(@Param(value = "node") Node node);

    int updateNode(@Param(value = "node") Node node);

    int updateNodeByMark(@Param(value = "node") Node node);

    int updateTemperAndHumidity(@Param(value = "nodeId") int nodeId,
                                @Param(value = "temper") float temper,
                                @Param(value = "humidity") float humidity);

    int deleteById(@Param("id") int id);

    int deleteByMark(@Param("mark") String mark);
}
