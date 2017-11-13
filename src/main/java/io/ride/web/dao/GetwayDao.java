package io.ride.web.dao;

import io.ride.web.entity.Getway;
import io.ride.web.entity.Node;
import org.apache.ibatis.annotations.Param;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午12:26
 */
public interface GetwayDao {
    /**
     * 判断网关是否已经存在
     *
     * @param mark mark
     * @return 存在true, 不存在false
     */
    boolean isExists(@Param("mark") String mark);

    /**
     * 根据实体添加一个网关节点
     *
     * @param getway 待添加实体
     * @return 更新数据条数
     */
    int addGetway(@Param(value = "getway") Getway getway);

    /**
     * 根据Id查找
     *
     * @param getwayId id
     * @return getway
     */
    Getway findById(Integer getwayId);

    /**
     * 根据节点盒子ID查找
     *
     * @param mark mark
     * @return getway
     */
    Getway findByMark(String mark);

    /**
     * 根据实体更新
     *
     * @param getway 实体
     * @return 更新数据条数
     */
    int updateGetway(@Param(value = "getway") Getway getway);

    /**
     * 根据实体mark更新数据
     *
     * @param getway 实例
     * @return 更新数目
     */
    int updateGetwayByMark(@Param(value = "getway") Getway getway);

    /**
     * 列出所有网关
     *
     * @return
     */
    List<Getway> list();


    /**
     * 得到网关的所有节点
     *
     * @param id id
     * @return 子节点
     */
    List<Node> listSubNode(int id);

    /**
     * mark
     * @param mark
     * @return
     */
    List<Node> listSubNodeByNodeMark(String mark);

    /**
     * 更新实时气温和湿度
     *
     * @param getwayId 网关Id
     * @param temper   温度
     * @param humidity 湿度
     * @return 更新条数
     */
    int updateTemperAndHumidity(@Param(value = "getwayId") int getwayId,
                                @Param(value = "temper") float temper,
                                @Param(value = "humidity") float humidity);

    /**
     * 根据Id删除网关
     *
     * @param id id
     * @return 更新数目
     */
    int deleteById(@Param("id") int id);

    /**
     * 根据mark删除网关
     *
     * @param mark mark
     * @return 更新数目
     */
    int deleteByMark(@Param("mark") String mark);
}
