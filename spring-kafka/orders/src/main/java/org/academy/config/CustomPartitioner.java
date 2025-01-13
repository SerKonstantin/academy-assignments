package org.academy.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomPartitioner implements Partitioner {

    /*
    Вообще в интернете пишут что Kafka по умолчанию использует Sticky Partitioner, который зачастую может быть
    более эффективным чем просто равномерное распределение. Так что это все просто для изучения.
    */

    @Override
    public void configure(Map<String, ?> configs) {}

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int partitionCount = cluster.partitionsForTopic(topic).size();
        return (key.hashCode() & Integer.MAX_VALUE) % partitionCount;
    }

    @Override
    public void close() {}
}
