--------------------------------
前缀树							|
--------------------------------
	# trie,也叫做字典树
	# 它不是二叉树,是一个多叉树
	# 它一般只用来处理字符串

	# 每个节点有多个指向下个节点的指针
		* 26个英文字母(拼音)
		* 如果考虑大小写的话,需要52个子节点

	# 节点的设计
		class Node {
			char c;
			Map<Character,Node> next;
		}