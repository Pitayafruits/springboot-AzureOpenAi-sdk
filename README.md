# 介绍
本项目是一个基于Springboot的一个后端服务，通过整合调用Azure OpenAI服务，实现一个具有上下文连续对话的AI助手，并且可以通过预置语料实现一小部分特定内容的解释：如企业信息、个人信息、AI的名称等。项目暂时只提供后端服务，有使用需求需要自行进行前端代码
的编写。
# 项目配置
**本项目有三个需要配置的点：** </br>
- application.yml -> Azure Key、MySQL、Redis
- ChatConstant.java -> 添加自己需求的预置语料
- ChatHistoryService.java -> 根据自己添加预置语料的数量，修改index的值
# 关于项目
项目目前算是一个sdk的简单封装，可能还有许多需要完善的地方，如果你有好的想法和建议，欢迎提Issues！
