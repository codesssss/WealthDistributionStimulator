import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# 读取CSV文件
df = pd.read_csv('WealthNum.csv')

# 对财富进行排序
df = df.sort_values(by='Wealth')

# 计算累积的人口比例和财富比例
df['cumulative_total'] = df['Population'].cumsum()
df['cumulative_wealth'] = df['Wealth'].cumsum()

df['cumulative_total_pct'] = df['cumulative_total'] / df['Population'].sum()
df['cumulative_wealth_pct'] = df['cumulative_wealth'] / df['Wealth'].sum()

# 计算基尼系数
area_under_lorenz_curve = np.trapz(df['cumulative_wealth_pct'], df['cumulative_total_pct'])
area_under_equality_line = 0.5  # This is the area under the line of perfect equality
gini_coefficient = 1 - 2 * (area_under_equality_line - area_under_lorenz_curve)

print("Gini Coefficient:", gini_coefficient)

# 绘制洛伦兹曲线
plt.figure(figsize=(10,6))
plt.plot(df['cumulative_total_pct'], df['cumulative_wealth_pct'], label='Lorenz Curve')
plt.plot([0,1], [0,1], linestyle='--', label='Equality Line')

# 添加图例
plt.legend()

# 设置y轴标签
plt.ylabel('Cumulative Percentage of Wealth')

# 设置x轴标签
plt.xlabel('Cumulative Percentage of Population')

# 设置图表标题
plt.title('Lorenz Curve')

# 显示图表
plt.show()
