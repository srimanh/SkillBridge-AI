import React, { useState } from 'react';
import { ChevronDown, ChevronUp, CheckCircle2, AlertCircle, Award } from 'lucide-react';
import { clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';

function cn(...inputs) {
    return twMerge(clsx(inputs));
}

export const FeedbackCard = ({ evaluation }) => {
    const [isSampleOpen, setIsSampleOpen] = useState(false);

    if (!evaluation) return null;

    const { score, verdict, strengths, improvements, sampleBetterAnswer } = evaluation;

    const getScoreColor = (s) => {
        if (s <= 4) return 'text-rose-500 border-rose-100 bg-rose-50 dark:bg-rose-950/20 dark:border-rose-900/30';
        if (s <= 7) return 'text-amber-500 border-amber-100 bg-amber-50 dark:bg-amber-950/20 dark:border-amber-900/30';
        return 'text-emerald-500 border-emerald-100 bg-emerald-50 dark:bg-emerald-950/20 dark:border-emerald-900/30';
    };

    const getScoreProgressColor = (s) => {
        if (s <= 4) return 'bg-rose-500';
        if (s <= 7) return 'bg-amber-500';
        return 'bg-emerald-500';
    };

    return (
        <div className="space-y-6 w-full animate-in fade-in slide-in-from-bottom-4 duration-500">
            {/* Score & Verdict */}
            <div className={cn("p-8 rounded-3xl border transition-colors", getScoreColor(score))}>
                <div className="flex justify-between items-start mb-6">
                    <div>
                        <h3 className="text-sm font-bold uppercase tracking-widest opacity-70 mb-1">Evaluation Score</h3>
                        <div className="text-6xl font-black">{score}/10</div>
                    </div>
                    <div className="text-right">
                        <h3 className="text-sm font-bold uppercase tracking-widest opacity-70 mb-1">Verdict</h3>
                        <div className="text-2xl font-bold">{verdict}</div>
                    </div>
                </div>
                <div className="h-3 w-full bg-black/5 dark:bg-white/5 rounded-full overflow-hidden">
                    <div
                        className={cn("h-full transition-all duration-1000 ease-out", getScoreProgressColor(score))}
                        style={{ width: `${score * 10}%` }}
                    />
                </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {/* Strengths */}
                <div className="glass p-6 rounded-3xl">
                    <div className="flex items-center gap-2 mb-4 text-emerald-500">
                        <CheckCircle2 size={20} />
                        <h4 className="font-bold uppercase text-xs tracking-wider">Strengths</h4>
                    </div>
                    <ul className="space-y-3">
                        {strengths.map((item, i) => (
                            <li key={i} className="text-sm leading-relaxed opacity-80 flex gap-2">
                                <span className="text-emerald-500 font-bold">•</span>
                                {item}
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Improvements */}
                <div className="glass p-6 rounded-3xl">
                    <div className="flex items-center gap-2 mb-4 text-rose-500">
                        <AlertCircle size={20} />
                        <h4 className="font-bold uppercase text-xs tracking-wider">Improvements</h4>
                    </div>
                    <ul className="space-y-3">
                        {improvements.map((item, i) => (
                            <li key={i} className="text-sm leading-relaxed opacity-80 flex gap-2">
                                <span className="text-rose-500 font-bold">•</span>
                                {item}
                            </li>
                        ))}
                    </ul>
                </div>
            </div>

            {/* Sample Better Answer */}
            <div className="glass rounded-3xl overflow-hidden border border-transparent hover:border-indigo-500/20 transition-all">
                <button
                    onClick={() => setIsSampleOpen(!isSampleOpen)}
                    className="w-full p-6 flex items-center justify-between hover:bg-black/[0.02] dark:hover:bg-white/[0.02] transition-colors"
                >
                    <div className="flex items-center gap-3 text-indigo-500">
                        <Award size={20} />
                        <span className="font-bold">See Sample Better Answer</span>
                    </div>
                    {isSampleOpen ? <ChevronUp size={20} /> : <ChevronDown size={20} />}
                </button>
                {isSampleOpen && (
                    <div className="p-6 pt-0 border-t border-black/5 dark:border-white/5 bg-indigo-50/30 dark:bg-indigo-950/10">
                        <p className="text-sm leading-relaxed italic opacity-80 whitespace-pre-wrap">
                            {sampleBetterAnswer}
                        </p>
                    </div>
                )}
            </div>
        </div>
    );
};
