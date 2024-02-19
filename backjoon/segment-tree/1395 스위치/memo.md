[스위치](https://www.acmicpc.net/problem/1395)

---

세그먼트 트리를 사용하지만, Lazy Update 를 사용하는 세그먼트 트리를 구현해야 한다.

이는 세그먼트 트리에서 특정 값이 아니라 특정 구간의 값이 갱신이 존재할 때, 세그먼트 트리의 조회가 일어날 떄와 비슷하게

노드의 구간이 갱신하는 구간 내에 전부 포함된다면 해당 노드까지만 갱신 작업을 수행하고, 하위 노드의 작업은 나중으로 미루는 것이다.

이 문제에서 갱신은 값의 0-1 스위치로 일어난다. 따라서 특정 노드에서 반전이 일어난다면, 해당 노드의 범위 만큼의 값에서 반전이 일어난다.

따라서 현재 범위가 1~5이고, 현재 값이 3이라면 반전이 일어나 3개의 스위치는 꺼지고 2개의 스위치는 켜질것이다.

따라서 스위치 결과값은 (right-left+1) - 현재값 이다.

lazyConvert 메서드는 이러한 반전 동작을 구현한다. 반전 동작이 수행될 때, 자식 노드가 존재한다면 현재 스위치 횟수를 자식에게 넘기고, 

반전을 수행한 후 스위치 횟수를 초기화한다.

lazyConvert 동작은 어떠한 노드를 방문할 때 가장 먼저 수행된다.

주의할 점은 이러한 동작은 범위를 벗어나는 노드를 방문할 때도 수행되어야 한다. 그렇지 않으면 convert 동작 시 하위 노드 두 개의 값을 더할 때,
아직 갱신되지 않은 노드를 참조하는 경우가 있다. 

```java
    private void convert(int left, int right, int tLeft, int tRight, int treeIdx) {
        lazyConvert(treeIdx, tLeft, tRight);
        if (left > tRight || right < tLeft) return;
    
        if (left <= tLeft && tRight <= right){
            convertCount[treeIdx]++;
            lazyConvert(treeIdx, tLeft, tRight);
            return;
        }
    
        int tMid = (tLeft + tRight) / 2;
        convert(left, right, tLeft, tMid, treeIdx * 2);
        convert(left, right, tMid+1, tRight, treeIdx * 2 + 1);
        
        // 이 값들 중 둘중 하나가 갱신되지 않았을 수 있다. 따라서 convert 함수의 최 상단에 언제나 lazyConvert 를 수행한다.
        tree[treeIdx] = tree[treeIdx*2] + tree[treeIdx*2 + 1];
    }
```

참조하는 하위 노드 두개는 언제나 lazyConvert 로 갱신되어 있어야 한다. 따라서 값을 벗어나서 아무 동작을 수행하지 않는 경우라도,
lazyConvert 에 의한 값의 갱신은 수행되어야 한다.
