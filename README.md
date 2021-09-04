# ElasticSearch

## 환경세팅

#### 개발(single Node)

```bash
docker network create elasticsearch
docker run -d --name elasticsearch2 --net elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.12.0
docker run -d --name cerebro --net elasticsearch -p 9000:9000 lmenezes/cerebro:0.9.4
#cerebro의 경우 이렇게 실행하면 네트워크를 통일하게 설정한다 하더라도 접근이 불가능함. 아직 원인은 모르겠음.
```

#### 배포(docker compose - 3개의 노드로 클러스터 구성)
max_map_count값을 지정해주라고 나온다. (windows의 경우)
```bash
wsl -d docker-desktop
sysctl -w vm.max_map_count=262144
#reference https://stackoverflow.com/questions/42111566/elasticsearch-in-windows-docker-image-vm-max-map-count
```

```yaml
version: '2.2'
services:
  es01:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.5.2
    container_name: es01
    environment:
      - node.name=es01
      - cluster.name=es-docker-cluster
      - discovery.seed_hosts=es02,es03
      - cluster.initial_master_nodes=es01,es02,es03
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms2g -Xmx2g"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - data01:/usr/share/elasticsearch/data
    ports:
      - "9200:9200"
    networks:
      - elastic
  es02:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.5.2
    container_name: es02
    environment:
      - node.name=es02
      - cluster.name=es-docker-cluster
      - discovery.seed_hosts=es01,es03
      - cluster.initial_master_nodes=es01,es02,es03
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms2g -Xmx2g"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - data02:/usr/share/elasticsearch/data
    networks:
      - elastic
  es03:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.5.2
    container_name: es03
    environment:
      - node.name=es03
      - cluster.name=es-docker-cluster
      - discovery.seed_hosts=es01,es02
      - cluster.initial_master_nodes=es01,es02,es03
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms2g -Xmx2g"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - data03:/usr/share/elasticsearch/data
    networks:
      - elastic
  cerebro:
    image: lmenezes/cerebro
    container_name: cerebro
    ports:
      - "9000:9000"
    networks:
      - elastic

volumes:
  data01:
    driver: local
  data02:
    driver: local
  data03:
    driver: local

networks:
  elastic:
    driver: bridge
```

celebro의 경우에 host에 설치에서 접속하면 node들이 보인다..

### 구성과정

Elastic Search에 등록을 하되 RabbitMQ를 통해서 순차적으로 데이터를 Post시키도록 함.

### Elastic Search 동작 과정

색인 --> 데이터 등록 --> 검색