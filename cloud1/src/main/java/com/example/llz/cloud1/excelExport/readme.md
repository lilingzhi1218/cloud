#1.excel 导出技术选型
## a.EasyExcel 
是阿里巴巴基于 Apache poi 开发的高性能 Excel 框架，使用 sax 模式读写性能稍差，但适合大数据量导入导出 

官网地址：https://easyexcel.opensource.alibaba.com/

## b.EasyPoi 
是一个开源的excel，word 框架，读写性能比 EasyExcel 好，但数据量一多就容易内存溢出

## c.Apache poi 是一个开源的 Java 库，用于处理 office 格式文件，不仅仅是 Excel

扩展：SAX模式‌是一种用于解析XML文档的模式，它提供了一种顺序访问机制，允许应用程序在读取XML文档时，通过触发一系列事件来处理XML数据。
这种模式被称为"推入"模式，因为解析器会将事件推送给应用程序，而不是像DOM模式那样将整个文档加载到内存中。
SAX模式的优势在于其对内存的要求较低，因为它不需要将整个XML文档加载到内存中，因此适合处理大型的XML文档